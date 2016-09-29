# React Native WhatsApp Contacts
React Native Package to get WhatsApp Contact for Android.
#### Status
* Preliminary Android support
* API subject to revision, changelog in release notes  

| Feature | iOS | Android |
| ------- | --- | ------- |
| `getAllWhatsAppContacts`  |  😞   | ✔ |



## API
`getAllWhatsAppContacts` (callback) - returns *all* WhatsAppContact contacts as an array of objects  

## Usage
`getAllWhatsAppContacts` is a database intensive process, and can take a long time to complete depending on the size of the contacts list. Because of this, it is recommended you access the `getAllWhatsAppContacts` method before it is needed, and cache the results for future use.

Also there is a lot of room for performance enhancements.

```js
import {
  NativeModules
} from 'react-native'

NativeModules.WhatsApp.getAllWhatsAppContacts((contacts) => {
    console.log(contacts)
})
```

## Example Contact Record
```js
{
  phone_no: '0123456789',
  name: 'Vikram Singh'
}
```

## Callback Contact Array of Contact Objects
```js
[ { phone_no: '0123456789', name: 'Vikram' },
  { phone_no: '0123456789', name: 'Singh Vikram' },
  { phone_no: '0123456789', name: 'Manpreet Kaur' },
  { phone_no: '0123456789', name: 'Munish Kumar' }]
```

## Getting started
run `npm install react-native-whatsapp-contacts`

### Android
* In `android/settings.gradle`
```gradle
...
include ':whatsapp-contact'
project(':whatsapp-contact').projectDir=newFile(settingsDir,'../node_modules/react-native-whatsapp-android/whatsappcontact')

```

* In `android/app/build.gradle`
```gradle
...
dependencies {
    ...
        compile project(':whatsapp-contact')
}
```

* register module (in android/app/src/main/java/[your-app-namespace]/MainApplication.java)
```java
	...

	import com.daffodilsw.whatsappcontact.RCTWhatsAppPackage; 	// <--- import module!

	public class MainActivity extends ReactActivity {
		...

	   	/**
	   	* A list of packages used by the app. If the app uses additional views
	   	* or modules besides the default ones, add more packages here.
	   	*/
	    @Override
	    protected List<ReactPackage> getPackages() {
	      return Arrays.<ReactPackage>asList(
	        new MainReactPackage(),
	        new RCTWhatsAppPackage() 	// <--- and add package
	      );
	    }

    	...
    }
```

* add Contacts permission (in android/app/src/main/AndroidManifest.xml)
(only add the permissions you need)
```xml
...
  <uses-permission android:name="android.permission.READ_CONTACTS" />
  <uses-permission android:name="android.permission.WRITE_CONTACTS" />
  <uses-permission android:name="android.permission.READ_PROFILE" />
...
```

## Todo
- [ ] android search contact feature
- [ ] implement `getAllWhatsAppContacts` for ios
