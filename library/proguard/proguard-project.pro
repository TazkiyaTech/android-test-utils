-keepparameternames

-keep public interface com.tazkiyatech.utils.test.** {
    <methods>;
}

-keep public class com.tazkiyatech.utils.test.** {
    public <init>(...);
    public <fields>;
    public static <fields>;
    public <methods>;
    public static <methods>;
}

-keep public enum com.tazkiyatech.utils.test.** {
    public <init>(...);
    public <fields>;
    public static <fields>;
    public <methods>;
    public static <methods>;
}
