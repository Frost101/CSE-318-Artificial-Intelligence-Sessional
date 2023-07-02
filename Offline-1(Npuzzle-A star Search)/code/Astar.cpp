#include<bits/stdc++.h>
using namespace std;

int direction[4][2] = {{-1,0},{1,0},{0,-1},{0,1}};  //Up-Down-Left-Right



class Npuzzle{

public:
    vector<vector<int>> board;
    int priority;
    int depth;
    int heuristic;
    Npuzzle *parent;
    int previous_move = -1;
    pair<int,int> zero_pos;

    Npuzzle(vector<vector<int>> board,int depth){
        this->board = board;
        this->depth = depth;
    }

    void calculate_priority(bool optimized = 1){
        if(optimized)
            priority = manhattan_distance() + depth;
        else
            priority = hamming_distance() + depth;
    }

    bool is_solvable(){
        int N = board.size();
        if(N%2){
            return ((inversion_count()%2)==0);
        }
        else{
            int temp = inversion_count();
            pair<int,int> zero_block = find_zero_block();
            temp += (N-1-zero_block.first);
            
            return (temp%2 == 0);
        }
    }

    int hamming_distance(){
        int N = board.size();
        int distance = 0;
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(board[i][j] != 0 && board[i][j] != ((i*N)+j+1)){
                    distance += 1;
                }
            }
        }
        heuristic = distance;
        return distance;
    }

    int manhattan_distance(){
        int N = board.size();
        int distance = 0;
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(board[i][j] != 0 && board[i][j] != ((i*N)+j+1)){
                    int x = board[i][j] - 1;
                    int row = x/N;
                    int column = x%N;
                    distance += (abs(row-i) + abs(column-j));
                }
            }
        }
        heuristic = distance;
        return distance;
    }

    int inversion_count(){
        int N = board.size();
        int count = 0;
        vector<int> temp;
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(board[i][j] != 0){
                    temp.push_back(board[i][j]);
                }
            }
        }

        for(int i=0;i<temp.size();i++){
            for(int j=i+1; j<temp.size(); j++){
                if(temp[j] < temp[i])count++;
            }
        }

        return count;
    }


    pair<int,int> find_zero_block(){
        pair<int,int> temp;
        int N = board.size();
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(board[i][j] == 0){
                    temp.first = i;
                    temp.second = j;
                    break;
                }
            }
        }
        zero_pos = temp;
        return temp;
    }

    void print(){
        int N = board.size();
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                cout << board[i][j] << " ";
            }
            cout << endl;
        }
        cout << endl;
    }

};


class Comparator{
    public:
        bool operator()(Npuzzle* x,Npuzzle* y){
            return x->priority > y->priority;
        }
};

int main()
{
    int N;
    cin>>N;
    vector<vector<int>> board(N);
    for(int i=0; i<N; i++){
        for(int j=0; j<N; j++){
            int x;
            cin>>x;
            board[i].push_back(x);
        }
    }

    priority_queue<Npuzzle*,vector<Npuzzle*>,Comparator> pq;
    Npuzzle* npuzzle = new Npuzzle(board,0);
    npuzzle->calculate_priority();
    npuzzle->find_zero_block();
    pq.push(npuzzle);


    if(!npuzzle->is_solvable()){
        cout << "Unsolvable Puzzle" << endl;
        return 0;
    }

    Npuzzle* ans;
    int expanded_nodes = 0;
    int explored_nodes = 1;

    while(!pq.empty()){
        expanded_nodes++;
        Npuzzle* tmp = pq.top();
        pq.pop();

        if(tmp->heuristic==0){
            ans = tmp;
            break;
        }

        int old_row = tmp->zero_pos.first;
        int old_column = tmp->zero_pos.second;

        for(int i=0; i<4; i++){
            int new_row = old_row + direction[i][0];
            int new_column = old_column + direction[i][1];
            if(new_row >= 0 && new_row < N && new_column >= 0 && new_column < N){

                if(tmp->previous_move == 0 && i == 1)continue;
                else if(tmp->previous_move == 1 && i == 0)continue;
                else if(tmp->previous_move == 2 && i == 3)continue;
                else if(tmp->previous_move == 3 && i == 2)continue;

                explored_nodes++;
                Npuzzle* child = new Npuzzle(tmp->board,tmp->depth + 1);
                swap(child->board[old_row][old_column],child->board[new_row][new_column]);
                child->zero_pos.first = new_row;
                child->zero_pos.second = new_column;
                child->calculate_priority();
                child->parent = tmp;
                child->previous_move = i;
                pq.push(child);
            }
        }
    }

    /*   To print number of explored nodes and expanded nodes   */
    //cout << "Number of expanded nodes = " << expanded_nodes << endl;
    //cout << "Number of explored nodes = " << explored_nodes << endl;

    stack<Npuzzle*> stk;
    stk.push(ans);
    int move = 0;
    while(ans->parent!=NULL){
        move++;
        stk.push(ans->parent);
        ans = ans->parent;
    }

    cout << "Minimum number of moves = " << move << endl;
    cout << endl;

    while(!stk.empty()){
        stk.top()->print();
        stk.pop();
    }
    return 0;
}
