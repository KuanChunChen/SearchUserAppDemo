# SearchUserAppDemo

Preface
----------
本repo內主要包含了android kotlin 100%的app<br>
內容主要是以mvvm的架構<br>
搭配dagger去完成<br>
串接Github api
並實現paging使其能自動加載分頁

Curl
----------
```shell
curl \
  -H "Accept: application/vnd.github.v3+json" \
  https://api.github.com/search/users
```

Library
----------
主要使用以下lib完成
* paging
* okhttp
* retrofit
* rxjava
* dagger
* gson
* ...etc

Features
----------
look like:
<div align="center">
  <img src="/demo/b.png" width="50%"/>
</div><br>

package structure:

<div align="center">
  <img src="/demo/a.png" width="50%"/>
</div><br>
