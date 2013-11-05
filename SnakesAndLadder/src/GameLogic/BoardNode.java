/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

/**
 *
 * @author Dylan
 */
public class BoardNode {
    int boardPos;
    boolean containsSnakeHead;
    boolean containsLadderBottom;
    boolean containsLadderTop;
    boolean containsSnakeTail;
    boolean containsStar;
    //boolean containsPlayer;
    static int numNodes = 0;
    
    BoardNode(){
        this.boardPos = numNodes;
        this.containsLadderBottom = false;
        this.containsSnakeHead = false;
	this.containsStar = false;
        numNodes++;
    }
    
    BoardNode(int specifiedPos){        
        this.boardPos = specifiedPos;
        this.containsLadderBottom = false;
        this.containsSnakeHead = false;
	this.containsStar = false;
        numNodes++;
    }
}
   
