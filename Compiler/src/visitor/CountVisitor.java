package visitor;

import ast.AST;
import java.util.ArrayList;
import java.util.List;

/**
 * CountVisitor is used to count the number of nodes and depth of an AST
 */

public class CountVisitor extends ASTVisitor {
    
    private int [] nCount = new int[100];
    private int depth = 0;
    private int maxDepth = 0;
    
    /**
     * Goes deeper into the tree and checks the max depth
     * @param t The AST to be traversed
     */
    private void count(AST t) {
        nCount[depth]++;
        
        if(depth > maxDepth) {maxDepth = depth;}
        
        depth++;
        visitKids(t);
        depth--;
    }
    
    /**
     * Getter method for array
     * @return The array with count of each level
     */
    public int[] getCount() {
        int [] count = new int[maxDepth + 1];
        
        for(int i = 0; i <= maxDepth; i++) {
            count[i] = nCount[i];
        }
        
        return count;
    }
    
    /**
     * Prints out the number of nodes per level of the tree
     */
    public void printCount() {
        for(int i = 0; i <= maxDepth; i++) {
            System.out.println("Depth: " + i + " Nodes: " + nCount[i]);
        }
    }

    public Object visitProgramTree(AST t) {count(t); return null;}
    public Object visitBlockTree(AST t) {count(t); return null;}
    public Object visitFunctionDeclTree(AST t) {count(t); return null;    }
    public Object visitCallTree(AST t) {count(t); return null;    }
    public Object visitDeclTree(AST t) {count(t); return null;    }
    public Object visitIntTypeTree(AST t) {count(t); return null;    }
    public Object visitBoolTypeTree(AST t) {count(t); return null;    }
    public Object visitFormalsTree(AST t) {count(t); return null;    }
    public Object visitActualArgsTree(AST t) {count(t); return null;    }
    public Object visitIfTree(AST t) {count(t); return null;    }
    public Object visitWhileTree(AST t) {count(t); return null;    }
    public Object visitReturnTree(AST t) {count(t); return null;    }
    public Object visitAssignTree(AST t) {count(t); return null;    }
    public Object visitIntTree(AST t) {count(t); return null;    }
    public Object visitIdTree(AST t) {count(t); return null;    }
    public Object visitRelOpTree(AST t) {count(t); return null;    }
    public Object visitAddOpTree(AST t) {count(t); return null;    }
    public Object visitMultOpTree(AST t) {count(t); return null;    }
    public Object visitUnaryOpTree (AST t) {count(t); return null;    }
    //new methods here
    public Object visitFloatTree(AST t) {count(t); return null;    }
    public Object visitFloatTypeTree(AST t) {count(t); return null;    }
    public Object visitDoWhileTree(AST t) {count(t); return null;   }
    public Object visitCharTypeTree(AST t) {count(t); return null;  }
    public Object visitCharTree(AST t) {count(t); return null;    }
    public Object visitScientificNTree(AST t) {count(t); return null;   }
    
}
