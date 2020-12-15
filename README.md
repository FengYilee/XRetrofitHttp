# XRetrofitHttp

使用手册:

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}

Step 2.添加版本

dependencies {
	        implementation 'com.github.FengYilee:XRetrofitHttp:1.1.0'
}


Step 3. 开启databinding （不然会报错）

dataBinding {
        enabled true
}

step 4. 初始化加载框（不然无法调用请求对话框）
override fun onCreate(savedInstanceState: Bundle?) {
	super.onCreate(savedInstanceState)
	setContentView(getLayoutById())
	LoadingDialogManager.getInstance().setDialog(LoadingDialog(this))
}
