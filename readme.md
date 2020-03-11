### 相关流程说明
#### 客户端
- 发起请求获取 code
- 根据服务端返回的 code 获取 token
- 根据token 获取相关的资源

### 服务端
- 根据客户端请求 下发 code
- 根据客户端code 下发 token
- 根据token 获取相关资源

### 说明
相关code 和 token 都需要 开发人员 自己保存和校验，
一般来说保存到缓存服务器中，如redis,从redis中获取值来进行校验

### 参考网址 
- https://www.liangzl.com/get-article-detail-10848.html

### 扩展点 security-oauth2 学习

- https://github.com/liangxiaobo/test-security-oauth2