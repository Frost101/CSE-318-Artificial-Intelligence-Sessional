#include<bits/stdc++.h>
using namespace std;

int direction[4][2] = {{-1,0},{1,0},{0,-1},{0,1}};  //Up-Down-Left-Right



class Npuzzle{

public:
    vector<vector<int>> board;
    int hamDistance;
    int manDistance;
    int priority;
    int depth;
    Npuzzle *parent;

    Npuzzle(vector<vector<int>> board,int depth){
        this->board = board;
        this->depth = depth;
        hamDistance = this->hamming_distance();
        manDistance = this->manhattan_distance();
        this->priority = manDistance + depth;
    }

    void calculate_priority(){
        priority = manDistance + depth;
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
        hamDistance = distance;
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
        manDistance = distance;
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
                }
            }
        }
        return temp;
    }

    void print(){
        int N = board.size();
        cout << endl;
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

    int moves = 0;
    priority_queue<Npuzzle*,vector<Npuzzle*>,Comparator> pq;
    Npuzzle* npuzzle = new Npuzzle(board,moves);
    pq.push(npuzzle);


    if(!npuzzle->is_solvable()){
        cout << "Unsolvable Puzzle" << endl;
        return 0;
    }

    Npuzzle* ans;

    while(!pq.empty()){
        moves++;
        Npuzzle* tmp = pq.top();
        pq.pop();

        if(tmp->hamming_distance()==0){
            cout << tmp->depth << endl;
            ans = tmp;
            break;
        }

        pair<int,int> zero_block = tmp->find_zero_block();
        int old_row = zero_block.first;
        int old_column = zero_block.second;

        for(int i=0; i<4; i++){
            int new_row = old_row + direction[i][0];
            int new_column = old_column + direction[i][1];
            if(new_row >= 0 && new_row < N && new_column >= 0 && new_column < N){
                Npuzzle* child = new Npuzzle(tmp->board,moves);
                swap(child->board[old_row][old_column],child->board[new_row][new_column]);
                child->hamming_distance();
                child->manhattan_distance();
                child->calculate_priority();
                child->parent = tmp;
                pq.push(child);
            }
        }
    }

    stack<Npuzzle*> stk;
    stk.push(ans);
    while(ans->parent!=NULL){
        stk.push(ans->parent);
        ans = ans->parent;
    }

    while(!stk.empty()){
        stk.top()->print();
        stk.pop();
    }

}