// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas


/**
 * The abstract superclass of all nodes in the abstract syntax tree (AST).
 */

abstract class JAST {

    /** Current compilation unit (set in JCompilationUnit()). */
    //public static JCompilationUnit compilationUnit;

    /** Line in which the source for the AST was found. */
    protected int line;

    /**
     * Construct an AST node the given its line number in the source file.
     * 
     * @param line
     *            line in which the source for the AST was found.
     */

    protected JAST(int line) {
        this.line = line;
    }

    /**
     * Return the line in which the source for the AST was found.
     * 
     * @return the line number.
     */

    public int line() {
        return line;
    }

    /**
     * Perform semantic analysis on this AST. In some instances a new returned
     * AST reflects surgery.
     * 
     * @param context
     *            the environment (scope) in which code is analyzed.
     * @return a (rarely modified) AST.
     */

    //public abstract void writeToStdOut(PrettyPrinter p);

}
