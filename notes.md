- Only generate moves for the color up
- Switch color
- Seperate color into different list moves.
- reverse rays
- remove moves that capture the king

Optimization Ideas:
- Instead of looping over all indexes, keep track of the index of each piece (DONE)
- Instead of splitting each move from the move list into different colors, only append to the color lists (MIGHT BE FIXED)
- Apply efficient search algorithms that search for the index
- reverse ray 
- remove moves that capture the king
- Bitboards?

To Do:
- For reverse rays impliment pawns and kings
- Implement isKingInCheck
- Implement movePutsKingInCheck


[52, 36]
[10, 18]
[60, 52]
[3, 10]
[52, 44]
[10, 17]
[51, 35]
Expected Outcome: [17, 44]

apply black legalization
remove moves that capture the king
![alt text](image.png) Number of moves for each depth