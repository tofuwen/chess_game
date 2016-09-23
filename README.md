# chess_game

The source code for gui-based chess game.

To run the code, run game/Game.java.

TODO:

1. add some synchronization.
Now, this code may suffer from race conditions.

2. add ui for the text.
Now, this code use System.out.printlm(), which is really ugly.

3. add more chess-related checks.
Such as when the king is in check, we cannot move an irrelent chess piece.

4. add promotion for pawn / castling for the king & rooks
