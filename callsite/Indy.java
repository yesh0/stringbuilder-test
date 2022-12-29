import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.StringConcatFactory;

public class Indy {
    public static void main(String[] args) throws Throwable {
        CallSite callSite = StringConcatFactory.makeConcatWithConstants(
                MethodHandles.lookup(),
                "",
                MethodType.methodType(String.class, String.class, String.class, String.class),
                "[[[ \u0001\u0002 \u0001\u0002 \u0001 ]]]",
                "!",
                "~"
        );
        System.out.println((String) callSite.dynamicInvoker().invokeExact("Hello", "World", "Invoke Dynamic"));
    }
}
