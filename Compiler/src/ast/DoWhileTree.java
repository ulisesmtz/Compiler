package ast;

import visitor.*;

public class DoWhileTree extends AST {

    public DoWhileTree() {
    }

    public Object accept(ASTVisitor v) {
        return v.visitDoWhileTree(this);
    }
}

