// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

/**
 * The AST node for an expression. The syntax says all expressions are
 * statements, but a semantic check throws some (those without a side-effect)
 * out.
 * 
 * Every expression has a type and a flag saying whether or not it's a
 * statement-expression.
 */

abstract class JExpression extends JStatement {

    /** Expression type. */
    //protected Type type;

    /** Whether or not this expression is a statement. */
    protected boolean isStatementExpression;

    /**
     * Construct an AST node for an expression given its line number.
     * 
     * @param line
     *            line in which the expression occurs in the source file.
     */

    protected JExpression(int line) {
        super(line);
        isStatementExpression = false; // by default
    }

    /**
     * Return the expression type.
     * 
     * @return the expression type.
     */
/*
    public Type type() {
        return type;
    }
    */

    /**
     * Is this a statementRxpression?
     * 
     * @return whether or not this is being used as a statement.
     */

    public boolean isStatementExpression() {
        return isStatementExpression;
    }

}
