#include<bits/stdc++.h>
using namespace std;


class Npuzzle{

public:
    vector<vector<int>> board;
    int hamDistance;
    int manDistance;
    Npuzzle *parent;

    Npuzzle(vector<vector<int>> &board){
        this->board = board;
        hamDistance = this->hamming_distance();
        manDistance = this->manhattan_distance();
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

    Npuzzle npuzzle(board);
    cout << npuzzle.is_solvable()<< endl;
}