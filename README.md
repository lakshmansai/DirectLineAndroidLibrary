> About:

Android Library for integeration of bot framework in android

**Asynchronous network call**

**Uses Retrofit**

**Uses Okhttp WebSocketListener instead of polling**

    Usage:

Add `maven { url 'https://jitpack.io' }` in root gradle
ex:

    allprojects {
        repositories {
            jcenter()
            maven { url 'https://jitpack.io' }
        }
    }

**Add library in module gradle**
`
    compile 'com.github.lakshmansai:DirectLineAndroidLibrary:1.1'
`

**Configure directline**

https://docs.botframework.com/en-us/restapi/directline3/
Get the primary key.

**Add permission in manifest**

    <uses-permission android:name="android.permission.INTERNET"></uses-permission>

**Add ChatView in MainActivity**
		

    final String botName="DocBot";                                                                    
            final String directlinePrimaryKey="DLfYFUt_9nM.****************************************************";
            View v = new ChatView(this,botName,directlinePrimaryKey);
            setContentView(v);

Credits:
https://github.com/brijrajsingh/DirectLineAndroidSample 
