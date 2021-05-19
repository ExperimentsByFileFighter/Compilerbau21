package compilerbau21;

import compilerbau21.compiler.CompileEnv;

public class InterpreterManualMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		CompileEnv compiler = new CompileEnv("InterpreterManualInput.txt");
		compiler.compile();
		compiler.execute(System.out);
		System.out.println("END");
	}

}
