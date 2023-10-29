import org.example.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReverseSubstringTest {

	@Test
	public void test() {
		String firstCase = "abd(jnb)asdf";
		String firstOutput = Main.solution(firstCase);
		String firstExpectedSolution = "abd(bnj)asdf";
		Assertions.assertEquals(firstExpectedSolution, firstOutput);

		String secondCase = "abdjnbasdf";
		String secondOutput = Main.solution(secondCase);
		String secondExpectedSolution = "abdjnbasdf";
		Assertions.assertEquals(secondExpectedSolution, secondOutput);

		String thirdCase = "dd(df)a(ghhh)";
		String thirdOutput = Main.solution(thirdCase);
		String thirdExpectedSolution = "dd(fd)a(hhhg)";
		Assertions.assertEquals(thirdExpectedSolution, thirdOutput);


		String fourthCase = "(abcdef)";
		String fourthOutput = Main.solution(fourthCase);
		String fourthExpectedSolution = "(fedcba)";
		Assertions.assertEquals(fourthExpectedSolution, fourthOutput);

	}
}
