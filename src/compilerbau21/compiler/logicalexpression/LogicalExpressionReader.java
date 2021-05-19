package compilerbau21.compiler.logicalexpression;

public enum LogicalExpressionReader implements LogicalExpressionInterface {

    /**
     * Parses a logical expression
     * valid operands:
     * ||, |, &&, &, ~, !
     * Grammar:
     * logicalExpression: ! EXPRESSION
     * logicalExpression: ~ EXPRESSION
     * logicalExpression: EXPRESSION && EXPRESSION
     * logicalExpression: EXPRESSION || EXPRESSION
     * logicalExpression: EXPRESSION & EXPRESSION
     * logicalExpression: EXPRESSION | EXPRESSION
     *
     * @see <a href='https://introcs.cs.princeton.edu/java/11precedence/'>Operator Precedence in Java</a>
     * Precedence:
     * 16 ()
     * 14 !~- (unary)
     * (ka) *
     * 11 + -
     * -----------------------------
     * ^ this is already implemented
     * -----------------------------
     * 7 &
     * 5 |
     * 4 &&
     * 3 ||
     */

    OR() {
        @Override
        public void parseExpression() throws Exception {

        }
    }
}
