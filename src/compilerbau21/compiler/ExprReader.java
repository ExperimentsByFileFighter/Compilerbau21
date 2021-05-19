package compilerbau21.compiler;

import compilerbau21.compiler.logicalexpression.instructions.And;
import compilerbau21.compiler.logicalexpression.instructions.BitWiseAnd;
import compilerbau21.compiler.logicalexpression.instructions.BitWiseOr;
import compilerbau21.compiler.logicalexpression.instructions.Or;

public class ExprReader extends ExprReaderIntf {


    public ExprReader(SymbolTable symbolTable, LexerIntf lexer, CompileEnvIntf compileEnv) throws Exception {
        super(symbolTable, lexer, compileEnv);
    }

    /**
     * Entry point for the expression reader!
     *
     * @throws Exception
     */
    public void getExpr() throws Exception {
        getLogicalOrExpression();
    }

    public void getAtomicExpr() throws Exception {
        // int number = 0;
        Token token = m_lexer.lookAheadToken();
        if (token.m_type == Token.Type.INTEGER) {
            m_lexer.advance();
            // number = token.m_intValue;
            InstrIntf numberInstr = new Instr.PushNumberInstr(token.m_intValue);
            m_compileEnv.addInstr(numberInstr);
        } else if (token.m_type == Token.Type.LPAREN) {
            m_lexer.advance();
            //number = getExpr();
            getExpr();
            m_lexer.expect(Token.Type.RPAREN);
        } else if (token.m_type == Token.Type.IDENT) {
            m_lexer.advance();
            //Symbol var = m_symbolTable.getSymbol(token.m_stringValue);
            //number = var.m_number;
            InstrIntf variableInstr = new Instr.VariableInstr(token.m_stringValue);
            m_compileEnv.addInstr(variableInstr);
        } else {
            throw new ParserException("Unexpected Token: ", token.toString(), m_lexer.getCurrentLocationMsg(), "numerical expression");
        }
        // return number;
    }

    // compile time des Programs
    public void getUnaryExpr() throws Exception {
        getAtomicExpr();
        Token token = m_lexer.lookAheadToken();
        while (token.m_type == Token.Type.MINUS) {
            m_lexer.advance();
            // create unary minus instruction
            InstrIntf unaryMinusInstr = new Instr.UnaryMinusInstr();
            // add instruction to code block
            m_compileEnv.addInstr(unaryMinusInstr);
            token = m_lexer.lookAheadToken();
        }
    }

    public void getProduct() throws Exception {
        // int number = getUnaryExpr();
        getUnaryExpr();
        Token token = m_lexer.lookAheadToken();
        while (token.m_type == Token.Type.MUL || token.m_type == Token.Type.DIV) {
            m_lexer.advance();
            if (token.m_type == Token.Type.MUL) {
                // number *= getUnaryExpr();
                getUnaryExpr();
                InstrIntf mulInstr = new Instr.MultiplyInstr();
                // add instruction to code block
                m_compileEnv.addInstr(mulInstr);
            } else {
                // number /= getUnaryExpr();
                getUnaryExpr();
                InstrIntf divInstr = new Instr.DivisionInstr();
                // add instruction to code block
                m_compileEnv.addInstr(divInstr);
            }
            token = m_lexer.lookAheadToken();
        }
        // return number;
    }

    public void getSum() throws Exception {
        getProduct();
        Token token = m_lexer.lookAheadToken();
        while (token.m_type == Token.Type.PLUS || token.m_type == Token.Type.MINUS) {
            m_lexer.advance();
            if (token.m_type == Token.Type.PLUS) {
                getProduct();
                InstrIntf addInstr = new Instr.AddInstr();
                // add instruction to code block
                m_compileEnv.addInstr(addInstr);
            } else {
                getProduct();
                InstrIntf subInstr = new Instr.SubInstr();
                // add instruction to code block
                m_compileEnv.addInstr(subInstr);
            }
            token = m_lexer.lookAheadToken();
        }
    }

    /**
     * checks for OR and creates instruction
     * calls AND
     *
     * @throws Exception
     */
    public void getLogicalOrExpression() throws Exception {
        getLogicalAnd();
        Token token = m_lexer.lookAheadToken();
        while (token.m_type == Token.Type.OR) {
            m_lexer.advance();
            getLogicalAnd();
            InstrIntf orInstr = new Or();
            // add instruction to code block
            m_compileEnv.addInstr(orInstr);
            token = m_lexer.lookAheadToken();
        }
    }

    /**
     * checks for AND
     * calls Bitwise OR
     */
    public void getLogicalAnd() throws Exception {
        getBitwiseOr();
        Token token = m_lexer.lookAheadToken();
        while (token.m_type == TokenIntf.Type.AND) {
            m_lexer.advance();
            getBitwiseOr();
            InstrIntf andInstruction = new And();
            // add instruction to code block
            m_compileEnv.addInstr(andInstruction);
            token = m_lexer.lookAheadToken();
        }
    }

    public void getBitwiseOr() throws Exception {
        getBitwiseAnd();
        Token token = m_lexer.lookAheadToken();
        while (token.m_type == TokenIntf.Type.BITOR) {
            m_lexer.advance();
            getBitwiseAnd();
            InstrIntf andInstruction = new BitWiseOr();
            // add instruction to code block
            m_compileEnv.addInstr(andInstruction);
            token = m_lexer.lookAheadToken();
        }
    }

    public void getBitwiseAnd() throws Exception {
        getSum();
        Token token = m_lexer.lookAheadToken();
        while (token.m_type == TokenIntf.Type.BITAND) {
            m_lexer.advance();
            getSum();
            InstrIntf andInstruction = new BitWiseAnd();
            // add instruction to code block
            m_compileEnv.addInstr(andInstruction);
            token = m_lexer.lookAheadToken();
        }
    }
}
