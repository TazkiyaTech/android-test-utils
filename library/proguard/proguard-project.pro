-keepparameternames

-keep public interface com.tazkiyatech.utils.** {
    <methods>;
}

-keep public class com.tazkiyatech.utils.** {
    public <init>(...);
    public <fields>;
    public static <fields>;
    public <methods>;
    public static <methods>;
}

-keep public enum com.tazkiyatech.utils.** {
    public <init>(...);
    public <fields>;
    public static <fields>;
    public <methods>;
    public static <methods>;
}
