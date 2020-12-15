# XRetrofitHttp

使用手册:

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}

Step 2. Add the dependency

dependencies {
	        implementation 'com.github.FengYilee:XRetrofitHttp:1.1.0'
}


Step 3. Add the databinding

dataBinding {
        enabled true
}
