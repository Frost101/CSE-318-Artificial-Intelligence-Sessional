#include<bits/stdc++.h>
using namespace std;


class Npuzzle{

public:
    vector<vector<int>> board;
    int hamDistance;
    int manDistance;
    pair<int,int> zero_block;
    Npuzzle *parent;
    Npuzzle *child;

    Npuzzle(vector<vector<int>> &board){
        this->board = board;
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
    cout << npuzzle.manhattan_distance() << endl;
}