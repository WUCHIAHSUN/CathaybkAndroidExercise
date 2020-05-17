# CathaybkAndroidExercise

這是一個APP，列出github使用者與管理員，點擊進入使用者詳細頁，詳細頁可察看使用者的個人介紹、所在地點、個人網頁。

## Features
1.當API呼叫過於頻繁，API會回傳等候一段時間，UI顯示"操作太頻繁"

2.當列表滑動到最下方時會顯示Loading...並載入下一頁的UsersList，當列表大於100筆則不再載入新的使用者

## 3rd party library
Okhttp - Networking

https://github.com/square/okhttp
```java
implementation 'com.squareup.okhttp3:okhttp:4.6.0'
```
Picasso - ImageView Management

https://github.com/square/picasso
```java
implementation 'com.squareup.picasso:picasso:2.5.2'
```
Gson - JSON Serialization

https://github.com/google/gson
```java
implementation 'com.google.code.gson:gson:2.8.5'
```

## 串接API
● List - https://developer.github.com/v3/users/#get-all-users
```java
"https://api.github.com/users?since=" + since
```

● Detail https://developer.github.com/v3/users/#get-a-single-user

```java
"https://api.github.com/users/" + name
```

## Develop Environment
#### 開發工具 : Android Studio 3.6.3
#### 作業系統 : Windows 10
#### SDK : Android Q API level 29