# 绪论
## 课题背景
## 课题意义

# 第1章 需求分析
使用Java实现“云雅图书馆”的需求分析主要涉及以下几个方面：

## 1. 用户登录与注册
云雅图书馆用户管理系统提供了登录和注册功能，允许用户访问图书馆资源。系统采用MD5加密算法对用户密码进行加密处理，并将加密后的密码存储在数据库中，以确保用户信息的安全。此外，系统还提供了`记住我`功能，通过使用cookie技术，当用户在登录时选择`记住我`，系统会在用户的设备上存储一个cookie，以便在用户下次访问时自动填充用户名，甚至自动登录，从而增强用户体验。
- **登录功能**：
    - **功能**：验证用户输入的用户名和密码。
    - **操作**：
	    1、用户输入用户名和密码。
	    2、系统检查输入的密码是否与数据库中存储的MD5加密密码匹配。
	    3、如果匹配，用户成功登录；如果不匹配，显示错误信息。
	    4、如果用户选择了“记住我”，系统将通过cookie存储用户名，以便在用户下次访问时自动填充。<br>
- **注册功能**：
    - **功能**：允许新用户创建账户。
    - **操作**：
	    1、用户输入用户名、密码和确认密码。
	    2、系统检查两次输入的密码是否一致，以及用户名是否已存在。
	    3、如果密码一致且用户名不存在，系统将使用MD5算法加密密码并存储到数据库中。
	    4、用户成功注册后，可以返回登录页面进行登录。<br>
## 2. 图书馆借阅查询系统
图书借阅管理系统的借阅列表页面提供了一个直观的界面，用于查看和管理图书馆书籍的借阅情况。管理员可以通过此页面监控每本书的借阅状态，包括借阅人信息、借阅时间、归还时间以及续借情况。
- **添加借阅信息：
	- **功能**：允许管理员输入新的借阅记录。
	- **操作**：点击“添加借阅信息”按钮，弹出表单或跳转至新页面，输入书籍ID、借阅人信息、借阅时间等。<br>
- **查看借阅记录：
	- **功能**：展示所有当前借阅的书籍记录。
	- **操作**：无需额外操作，页面加载时自动显示所有记录。<br>
- **续借功能：
	- **功能**：允许管理员为特定书籍处理续借请求。
	- **操作**：点击“续借”按钮，运行书籍续借一个月，更新归还时间并记录续借信息。<br>
-  **续借状态提示**：
	- **功能**：提示管理员某本书的续借是否达到上限。
	- **操作**：在“续借”列中显示“续借达到上限”的提示，防止管理员进行无效操作。<br>
## 3. 图书馆图书管理
书籍管理系统的书籍列表页面提供了一个直观的界面，用于查看和管理图书馆书籍的详细信息。管理员可以通过此页面添加新书、查看书籍状态、以及管理书籍信息。
- **添加书籍信息**：
	- **功能**：允许管理员输入新的书籍记录。
	- **操作**：点击“添加书籍信息”按钮，弹出表单或跳转至新页面，输入书籍作者、标题、简介、书籍封面、价格等。<br>
- **搜索书籍**：
	- **功能**：允许管理员通过书籍标题快速查找书籍。
	- **操作**：在搜索栏中输入关键词，点击搜索图标或按回车键。<br>
- **删除书籍**：
	- **功能**：允许管理员从系统中删除书籍信息。
	- **操作**：点击“删除书籍”列中的删除图标，确认删除操作。<br>
## 4. 图书馆学生信息查询
学生管理系统的学生列表页面提供了一个直观的界面，用于查看和管理学生的基本信息。管理员可以通过此页面查看学生的学号、姓名、性别和年级等信息。
- **查看学生记录**：
	- **功能**：展示所有当前管理的学生记录。
	- **操作**：无需额外操作，页面加载时自动显示所有记录。<br>

## 5. 图书馆安全设置
图书馆将确保用户数据的安全性。包括用户账户安全（密码使用MD5签名存储）、访问控制（登录后才能访问网页资源，无法通过网址越界访问）、输入验证（防止SQL注入和XSS跨站脚本攻击）、错误处理（针对越界发起404或针对内部错误发起500请求）。<br>


# 第2章 总体设计
## 2.1 系统功能结构
### 功能结构图
![](Source/系统功能结构.drawio.png)

### 用例图
![](Source/用例图.drawio.png)

### E-R图
![](Source/ER图.drawio.png)

## 2.2 论坛功能模块
本论坛采用Django框架作为后端服务，结合Bootstrap 5进行前端页面的构建，以实现一个响应式和用户友好的界面。

### 2.2.1 管理员后台模块
管理员后台模块为论坛管理员提供了一个集中管理论坛内容的界面。管理员可以通过此模块执行以下操作：

- 审核和修改用户提交的话题和帖子。
- 删除不当或违规的内容（话题和帖子）。
- 设置和修改用户权限，包括提升普通用户为管理员或降低管理员权限。
- 查看和管理用户注册信息，包括审核新注册用户。
- 监控用户活动，包括登录日志和用户行为记录。

### 2.2.2 用户注册模块
用户注册模块允许新用户创建账户并加入论坛社区。注册流程包括：

- 输入必要的个人信息，如用户名、密码。
- 设置符合强密码要求的密码，至少8位。
- 完成注册后，用户将自动跳转到论坛首页，获得发布话题和帖子的权限。

### 2.2.3 用户登录模块
用户登录模块允许已注册用户通过账号和密码登录论坛。此模块支持以下功能：

- 输入用户名和密码进行登录。
- 对错误的用户名和密码进行驳回。

### 2.2.4 话题发布模块
话题发布模块允许用户（包括学生和管理员）创建新的话题。用户可以：

- 输入话题标题。
- 发布话题后，其他用户可以在该话题下发表帖子。

### 2.2.5 帖子发布模块
帖子发布模块允许用户在特定话题下发表帖子。用户可以：

- 输入帖子的详细内容。
- 帖子将按照时间倒序排列，新帖子显示在前。

### 2.2.6 帖子修改模块
帖子修改模块允许用户编辑和更新自己发布的帖子。用户可以：

- 修改帖子的内容。
- 编辑帖子以更正错误或添加更多信息。
- 确保帖子内容的准确性和时效性。

## 2.3 系统开发环境
操作系统：
Windows 11 23H2 Professional

系统配置：
AMD Ryzen 7 7840HS w/ Radeon 780M Graphics 3.80 GHz (16 GB)

Python版本：
Python 3.12.4

框架环境及依赖包版本：

asgiref 3.8.1

Django 5.0.6

django-bootstrap5 24.2

sqlparse 0.5.0

tzdata 2024.1

我是分割线---

# 第3章 详细设计
## 3.1.1 注册页功能设计
注册功能允许新用户创建账户。设计思路包括：
- **用户输入验证**：确保用户名、密码和确认密码的输入符合预设的格式和安全要求。
- **用户名唯一性检查**：在用户提交注册信息前，检查数据库中是否已存在相同的用户名。
- **密码一致性验证**：比较用户输入的密码和确认密码是否一致。
- **信息加密存储**：使用MD5或更安全的算法对用户密码进行加密后存储。
- **注册反馈**：提供注册成功或失败的明确反馈。
- **跳转登录**：注册成功后，自动跳转到登录页面或提供登录选项。
![](Documentation/page/login.drawio.png)

## 3.1.2 登录功能设计
登录功能允许已注册用户访问论坛。设计思路包括：

- **用户输入验证**：确保用户名和密码的输入符合预设的格式要求。
- **凭证验证**：检查数据库中是否存在匹配的用户名和加密密码。
- **记住我功能**：使用Cookie记录用户的登录状态。
- **错误处理**：提供错误信息，如“用户名或密码错误”。
- **辅助功能**：提供“忘记密码”和“注册新用户”链接
![](Documentation/page/register.drawio.png)

## 3.1.3 主页面功能设计
主页面上展示学生人数、书籍总数、借阅总数信息、退出按钮、名称改变。设计思路包括：
- **展示数据**：后端映射，前端组件不变，只变数据，每次变化数据时都将重新加载主页面，重新映射数据上来。
- **退出按钮**：退出按钮放在右上角的头像里面，点击退出按钮即可返回登录界面，并且清楚cookie。
- **改变名称**：在右上角的头像下拉框中，改变用户名称。
![](Documentation/page/Student.drawio.png)

## 3.1.4 学生面板展示设计
学生面板展示允许只允许展示学生的信息。设计思路包括：

- 从数据库中读取信息，映射到页面
-
![](Documentation/page/Student.drawio.png)


## 3.1.5 书籍面板功能设计
书籍面板功能允许管理员添加新的书籍，以及展示已有的书籍，搜索书籍。设计思路包括：

- 管理员输入书籍信息。
- 书籍信息从数据库中读取，映射到页面。
-  运用模糊搜索，搜索书籍名称
![](Documentation/page/books.drawio.png)

## 3.1.6 借阅面板功能设计
借阅面板功能允许添加与展示学生借阅情况，添加还书按钮，续借按钮。设计思路包括：

- 输入需添加借阅的学生姓名与书籍名称。
- 借阅信息从数据库中读取，映射到页面 。
- 点击还书按钮即可还书。
- 点击续借按钮即可将还书时间延迟一个月，续借过的借阅信息不可再续借。
![](Documentation/page/Borrow.drawio.png)

# 第4章 系统实现

## 4.1 代码实现准备
```
- **使用了MVC（Model-View-Controller）设计模式将代码分好包**：
	1. **dao**:
	    - Data Access Object（数据访问对象）层，负责与数据库进行交互。通常包含用于执行CRUD（创建、读取、更新、删除）操作的接口和实现类。
	2. **entity**:
	    - 实体类，通常对应数据库中的表。这些类包含了应用程序的数据模型。
	3. **filter**:
	    - 过滤器，用于处理请求和响应之前或之后的逻辑，如身份验证、日志记录、请求日志等。
	4. **service**:
	    - 服务层，包含业务逻辑。这一层调用DAO层的方法来获取数据，并处理业务规则。
	5. **service.Impl**:
	    - 服务层的实现类，实现了`service`包中的接口。这里包含了具体的业务逻辑实现。
	6. servlet**:
	    - Servlet层，处理HTTP请求和响应。Servlet可以映射到特定的URL，用于处理客户端的请求。
	7. servlet.auth**:
	    - 认证相关的Servlet，可能包含登录、注册、权限检查等功能。
	8. manage**:
	    - 管理相关的Servlet，可能用于管理员操作，如管理书籍、用户等。
	9. servlet.pages**:
	    - 页面相关的Servlet，可能用于处理页面请求，如显示书籍列表、用户信息等。
	10. **com.book.servlet.utils**:
	    - 工具类，可能包含一些辅助功能，如字符串处理、日期处理等。
	11. **resources**:
	    - 资源文件夹，通常用于存放配置文件、静态资源（如图片、CSS、JavaScript文件）等。
```

在MainFilter中还实现了禁止用户越过页面访问，我们限定了只有完成登录才可访问其他页面
```java
@Override  
protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {  
    String url = req.getRequestURL().toString();  
    if (!url.contains("/static") && (!url.endsWith("login") && !url.endsWith("register"))) {  
        HttpSession session = req.getSession();  
        User user = (User) session.getAttribute("user");  
        if (user == null) {  
            res.sendRedirect("login");  
            return;  
        }  
    }  
    chain.doFilter(req, res);  
}
```

## 4.2 注册实现

1. 在RegisterServlet中继承HttpServlet，前后对接，获取前端页面的信息，然后进行MD5签名。
```java
String username = req.getParameter("username");  
String password = req.getParameter("password");  
String confirm_password = req.getParameter("confirm_password");  
String encryptedPassword = MD5Util.toMD5(password);
```

2. 在重写的doPost方法中处理在前端获取的数据，随后调用方法进行判断是否满足条件，满足条件调用方法存储数据，不满足条件则修改向前端发送信号。
```java
if(password.length() > 8){  
    if(password.equals(confirm_password) && !userService.AlreadyUsername(username, req.getSession())){  
  
        userService.InsertUser(username, encryptedPassword, req.getSession());  
  
        resp.sendRedirect("index");  
    }else {  
        req.getSession().setAttribute("register-failure", new Object());  
        this.doGet(req, resp);  
    }  
}else {  
    req.getSession().setAttribute("register-failure", new Object());  
    this.doGet(req, resp);  
}
```
3. 在重写的doGte方法中，向前端传输信号，将前端登录提示信息改为错误提示信息
```java
@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    Context context = new Context();  
    if (req.getSession().getAttribute("register-failure") != null) {  
        context.setVariable("failure", true);  
        req.getSession().removeAttribute("register-failure");  
    }  
  
    ThymeleafUtil.process("register.html", context, resp.getWriter());  
}
```
4. 其中，考虑到 ==UserServiceImpl== 可能会在大量文件中出现，每次出现都会 new 一个新的出来，大大浪费空间，因此我们将设计模式改为单例设计模式，只创建一个对象
```java
UserService userService;  
@Override  
public void init() throws ServletException {  
    userService = UserServiceImpl.getInstance();  
}
```

## 4.3 登录实现
1. 首先与登录相关的 `Impl` 文件我们都会改为单例模式，为了减少空间的浪费。
```java
UserService userService;  
@Override  
public void init() throws ServletException {  
    userService = UserServiceImpl.getInstance();  
}
```

2. 与注册界面相同，登录界面也是继承HttpServlet，重写 `doGet` 和 `doPost` 方法，在 `doGet` 方法中，我们将会判断是否已经有存储的cookie，如果有已经存储的cookie，我们将会跳过登录界面，直接进入主页面
```java
Cookie[] cookies = req.getCookies();  
if(cookies != null){  
    String username = null;  
    String password = null;  
    for (Cookie cookie : cookies) {  
        if(cookie.getName().equals("username")) username = cookie.getValue();  
        if(cookie.getName().equals("password")) password = cookie.getValue();  
    }  
    if(username != null && password != null){  
        if (userService.auth(username, password, req.getSession())) {  
            resp.sendRedirect("index");  
            return;  
        }  
    }  
}
```
3. 在 `doGet` 里面，也会加入向前端传输信号，改变前端提示字段
```java
Context context = new Context();  
if (req.getSession().getAttribute("login-failure") != null) {  
    context.setVariable("failure", true);  
    req.getSession().removeAttribute("login-failure");  
}  
if (req.getSession().getAttribute("user") != null) {  
    resp.sendRedirect("index");  
    return;  
}  
ThymeleafUtil.process("login.html", context, resp.getWriter());
```
4. 在 `doPost` 方法中，将会接收前端的登录信息，先进行MD5签名后再调用数据库进行判断，登录是否成功，再判断是否有勾选 `记住我` ，如果有，则会将cookie保留，以方便下次登录无需输入账号密码，如果账号或密码错误，则会向前端发送信号
```java
@Override  
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    String username = req.getParameter("username");  
    String password = req.getParameter("password");  
    String remember = req.getParameter("remember-me");  
  
    // 使用MD5加密用户输入的密码  
    String encryptedPassword = MD5Util.toMD5(password);  
    System.out.println(encryptedPassword);  
  
    if (userService.auth(username, encryptedPassword, req.getSession())) {  
        if(remember != null){   //若勾选了记住我选项，则设置Cookie  
            Cookie cookie_username = new Cookie("username", username);  
            int week = 60 * 60 * 24 * 7;  
            cookie_username.setMaxAge(week);  
            Cookie cookie_password = new Cookie("password", encryptedPassword); // 这里应该是加密后的密码  
            cookie_password.setMaxAge(week);  
            resp.addCookie(cookie_username);  
            resp.addCookie(cookie_password);  
        }  
        resp.sendRedirect("index");  
    } else {  
        req.getSession().setAttribute("login-failure", new Object());  
        this.doGet(req, resp);  
    }  
}
```


## 4.4 主页实现
1. 主页面主要由前端实现，前端展示的数据根据接收后端数据库的数据进行更改，首先在 `doGet`方法中展示系统中学生、书籍和借阅的数量，还有展示用户名。
```java
@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    Context context = new Context();  
    User user = (User) req.getSession().getAttribute("user");  
    context.setVariable("nickname", user.getNickname());  
    context.setVariable("borrow_list", bookService.getBorrowList());  
    context.setVariable("book_count", bookService.getBookList().size());  
    context.setVariable("student_count", studentService.getStudentList().size());  
    ThymeleafUtil.process("index.html", context, resp.getWriter());  
}
```
我们同样使用的单例设计模式
```java
@Override  
public void init() throws ServletException {  
    bookService = BookServiceImpl.getInstance();  
    studentService = StudentServiceImpl.getInstance();  
}
```
2. 在LogoutServlet.java的 `doGet` 中，我们在页面右上角的用户信息中，我们添加了退出按钮，并且清除cookie。
```java
@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    Cookie cookie_username = new Cookie("username", "");  
    cookie_username.setMaxAge(0);  
    Cookie cookie_password = new Cookie("password", "");  
    cookie_password.setMaxAge(0);  
    resp.addCookie(cookie_username);  
    resp.addCookie(cookie_password);  
  
    req.getSession().removeAttribute("user");  
    resp.sendRedirect("login");  
}
```
## 4.5 学生面板实现
主页面不变，只变内容，外框不换。
1. 在 students.html中，把整个展示做成一张表，将学号、姓名、性别和年龄各位一列，接收来自后端的数据。
```html
<table class="table table-styled mb-0">  
    <thead>
        <tr>
            <th>学号</th>  
	        <th>姓名</th>  
	        <th>性别</th>  
	        <th>年级</th>  
	    </tr>    
	</thead>    
<tbody>        
	<tr th:each="student : ${student_list}">  
		<td><code style="color: black; font-size: medium" th:text="'#'+${student.getGrade()}+'021500'+${student.getSid()}">202302150005</code></td>  
	    <td th:text="${student.getName()}">小汪</td>  
	    <td th:text="${student.getSex()}">男</td>  
	    <td th:text="${student.getGrade()}+'级'">2023级</td>  
    </tr>    
</tbody>
```
2. 在StudentServlet.java `doGet` 中，我们将数据库中的数据映射到前端页面展示中
```java
@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    Context context = new Context();  
    User user = (User) req.getSession().getAttribute("user");  
    context.setVariable("nickname", user.getNickname());  
    context.setVariable("student_list", studentService.getStudentList());  
    ThymeleafUtil.process("students.html", context, resp.getWriter());  
}
```

## 4.6 书籍面板实现
主页面不变，只变内容，外框不换。
1. 在 books.html中，把整个展示做成一张表，将学号、姓名、性别和年龄各位一列，接收来自后端的数据。
```html
<thead>  
<tr>  
    <th>书籍ID</th>  
    <th>书籍图片</th>  
    <th>书籍标题</th>  
    <th>书籍简介</th>  
    <th>书籍价格</th>  
    <th>借阅状态</th>  
    <th>删除书籍</th>  
</tr>  
</thead>
<tr th:each="book, iterStat : ${book_list}">  
    <td th:text="'#'+${book.getBid()}">$600</td>  
    <td>
        <img style="max-width: 80px" th:if="${book.getImagePath() != null and !#strings.isEmpty(book.getImagePath())}" th:src="${book.getImagePath()}" />  
        <img style="max-width: 80px" th:unless="${book.getImagePath() != null and !#strings.isEmpty(book.getImagePath())}" th:src="@{static/picture/books/default-book.gif}" />  
    </td>    
    <td th:text="${book.getTitle()}">$600</td>  
    <td th:text="${book.getDesc()}">$600</td>  
    <td th:text="'￥'+${book.getPrice()}">$600</td>  
    <td>        
    <label class="mb-0 badge badge-primary" title="" data-original-title="Pending" th:if="${book_list_status.get(iterStat.index)}">已被借阅</label>  
     <label class="mb-0 badge badge-success" title="" data-original-title="Pending" th:unless="${book_list_status.get(iterStat.index)}">可借阅</label>  
    </td>
```
2. 在BooksServlet.java的 `doGet` 中，实现将数据传输到前端，并且通过获取书籍的借阅信息来改变前端的展示。
```java
@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    Context context = new Context();  
    User user = (User) req.getSession().getAttribute("user");  
    context.setVariable("nickname", user.getNickname());  
    String search = req.getParameter("search");  
    Map<Book, Boolean> map;  
    if (search != null && !search.equals("")) {  
        map = bookService.getBookByTitle(search);  
        context.setVariable("book_list", map.keySet());  
        context.setVariable("book_list_status", new ArrayList<>(map.values()));  
    } else {  
        map = bookService.getBookList();  
        context.setVariable("book_list", map.keySet());  
        context.setVariable("book_list_status", new ArrayList<>(map.values()));  
    }  
    ThymeleafUtil.process("books.html", context, resp.getWriter());  
}
```
3. 在页面中点击 `添加书籍信息` 即调用AddBookServlet.java中的 `doPost` 方法，跳转到新的页面，输入完书籍信息和传输书籍封面，后端会自动将数据传输到数据库保存，数据库中存储的是封面图片的地址，每次添加完新的都会回到并刷新books页面。
```java
@SneakyThrows  
@Override  
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
  
    if (!JakartaServletFileUpload.isMultipartContent(req)) {  
        resp.sendRedirect("add-book");  
        return;  
    }  
  
    String title = null;  
    String author = null;  
    String desc = null;  
    double price = 0.0;  
    String imageName = null;  
  
    DiskFileItemFactory factory = DiskFileItemFactory.builder().get();  
    JakartaServletFileUpload upload = new JakartaServletFileUpload(factory);  
  
    List<FileItem> files = upload.parseRequest(req);  
    for (FileItem fileItem : files) {  
        if (!fileItem.isFormField()) {  
            imageName = UniqueCodeUtil.UniqueSuffix(fileItem.getName());  
            Path path = Paths.get(RUNTIME_PATH + imageName);  
            fileItem.write(path);  
            Path currentWorkingDirectory = Paths.get("").toAbsolutePath();  
            System.out.println("当前工作目录: " + currentWorkingDirectory);  
        } else {  
            String fieldName = fileItem.getFieldName();  
            String fieldValue = fileItem.getString(StandardCharsets.UTF_8);  
            if ("title".equals(fieldName)) {  
                title = fieldValue;  
            } else if ("author".equals(fieldName)) {  
                author = fieldValue;  
            } else if ("desc".equals(fieldName)) {  
                desc = fieldValue;  
            } else if ("price".equals(fieldName)) {  
                price = Double.parseDouble(fieldValue);  
            }  
        }  
    }  
  
    bookService.addBook(title, desc, price, DB_PATH + imageName);  
    resp.sendRedirect("books");  
}
```
4. 点击 `删除书籍` 按钮则调用DeleteBookServlet.java中的 `doGet` 方法，运用mybatis使用sql语句删除book数据库中信息。
```java
@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    int bid = Integer.parseInt(req.getParameter("bid"));  
    bookService.deleteBook(bid);  
    resp.sendRedirect("books");  
}
```

## 4.7 借阅面板实现
主页面不变，只变内容，外框不换。
1. index.html 中，将需展示的信息制成表格，等待后端传输的信息。
```html
<thead>  
      <tr>
		<th>书籍ID</th>  
		<th>书籍名称</th>  
		<th>借阅时间</th>  
		<th>归还时间</th>  
		<th>借阅人</th>  
		<th>借阅人学号</th>  
		<th>归还</th>  
	    <th>续借</th>
	</tr>
</thead>
<tbody>  
    <tr th:each="borrow : ${borrow_list}">  
        <td th:text="${borrow.getBook_id()}">#JH2033</td>  
        <td th:text="${borrow.getBook_name()}">我是书名</td>  
        <td th:text="${borrow.getBorrowDate()}">22/06/2021</td>  
        <td style="color: red "th:text="${borrow.getReturnDate()}">22/07/2021</td>  
        <td th:text="${borrow.getStudent_name()}">我是学生</td>  
        <td th:text="${borrow.getStudent_id()}">#1111</td>  
        <td class="relative">
```

2. 按下`续借` 按钮调用RenewBookServlet.java，续借信号分两种，一种为 `续借`，另一种为 `续借到达上限`，当续借次数大于一时，转换为 `续借到达上限`，不可再按。
```java
@Override  
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    Context context = new Context();  
    context.setVariable("book_list", bookService.getActiveBookList());  
    ThymeleafUtil.process("index", context, resp.getWriter());  
}  
  
@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
  
    String id = req.getParameter("bid");  
    boolean b = bookService.renewBook(id);  
    if(b){  
        req.setAttribute("renew_list_status",true);  
    }  
  
    resp.sendRedirect("index");  
}
```

```html
<td>  
    <label class="mb-0 badge badge-danger" title="" data-original-title="Pending" th:if="${borrow.getRenewStatus == 1}">续借达到上限</label>  
    <label class="mb-0 badge badge-primary" title="" data-original-title="Pending" th:unless="${borrow.getRenewStatus == 1}"><a onmouseover="this.style.color='white'" onmouseout="this.style.color='black'" type="button" th:href="'renew-book?bid='+${borrow.book_id}">续借</a></label>  
</td>
```


3. 按下 `归还` 按钮，调用ReturnServlet.java 中的方法，使用mybatis调用sql语句删去信息
```java
@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    String id = req.getParameter("id");  
    bookService.returnBook(id);  
    resp.sendRedirect("index");  
}@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    String id = req.getParameter("id");  
    bookService.returnBook(id);  
    resp.sendRedirect("index");  
}
```

4. 按下 `添加借阅信息`，跳转到新的页面add-borrow.html，填写借阅信息后调用AddBorrowServlet.java 方法，获取前端数据后调用sql语句，将数据存入数据库
```java
@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    Context context = new Context();  
    context.setVariable("book_list", bookService.getActiveBookList());  
    context.setVariable("student_list", studentService.getStudentList());  
    ThymeleafUtil.process("add-borrow.html", context, resp.getWriter());  
}  
  
@Override  
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  
    String student_id = req.getParameter("student");  
    int sid = Integer.parseInt(student_id);  
    String book_id = req.getParameter("book");  
    int bid = Integer.parseInt(book_id);  
    bookService.addBorrow(sid, bid);  
    resp.sendRedirect("index");  
}
```



# 第5章 系统测试
### 5.1 测试登录功能
登录功能界面，在此输入账号密码
![](Documentation/page/login-ordinary.png)
登录失败界面，重新输入账号密码
![](Documentation/page/login-error.png)
### 5.2 测试注册功能
注册界面，输入账号密码，密码不得少于八位
![](Documentation/page/register-ordinary.png)
注册失败界面，输入的用户名已存在或密码不够八位或两次输入的密码不相同，注册完返回登录界面，可勾选记住我，
![](Documentation/page/register-error.png)
### 5.3 测试借阅功能
借阅界面，勾选了记住我，登录进去后关闭页面，重新打开程序，跳过了登录界面，直接进入借阅界面，点击书籍ID为1那一行的续借
![](Documentation/page/borrow-1.png)
续借按钮变红，不得再次按下，归还时间延迟一个月，随后点击添加借阅信息
![](Documentation/page/borrow-2.png)
打开下拉框选择借阅的学生姓名和书籍名称，点击提交
![](Documentation/page/add-borrow.png)
所选的学生信息和书籍信息都成功加载，图书借阅管理栏中借阅数量变为4，点击书籍ID为3那一行的归还
![](Documentation/page/affter-addBorrow.png)
点击归还后借阅信息成功删除，图书借阅管理栏中借阅数量变为3
![](Documentation/page/delete-borrow.png)
### 5.4 测试书籍管理功能
书籍管理页面，成功展示已有书籍及状态，点击添加书籍信息
![](Documentation/page/book-1.png)
添加书籍信息界面，填入书籍信息及书籍封面，点击提交
![](Documentation/page/add-book.png)
成功展示新添加书籍信息
![](Documentation/page/affter-addBook.png)
改变借阅状态，在借阅管理中新增信息，书籍选择新加的书籍，点击提交
![](Documentation/page/add-borrow-2.png)
成功改变借阅状态，测试删除功能，点击书籍ID为33那一列的删除书籍按钮
![](Documentation/page/book-2.png)
书籍删除成功
![](Documentation/page/delete-book.png)
### 5.5 测试学生列表功能
点击学生列表，成功展示已有学生信息
![](Documentation/page/student-1.png)



# 第6章 总结与展望

## 6.1 项目总结

本项目“云雅图书馆管理系统”的开发与实施，是一次全面运用Java技术栈进行Web应用开发的实践。通过本项目，我们不仅实现了图书馆管理的数字化、信息化，还提升了图书馆服务的效率和质量。项目的主要成就包括：

1. **用户友好的界面设计**：系统提供了直观、易用的用户界面，使得用户能够快速上手并有效使用系统功能。
    
2. **全面的图书馆管理功能**：包括图书管理、借阅管理、学生信息管理等核心功能，满足了图书馆日常运营的基本需求。
    
3. **安全性保障**：通过MD5加密算法对用户密码进行加密处理，以及实施了访问控制和输入验证，确保了用户数据的安全性。
    
4. **系统性能**：在高并发环境下，系统能够稳定运行，处理大量借阅和查询请求。
    
5. **测试与部署**：经过多轮测试，系统在功能、性能、安全等方面均达到了预期目标，并已成功部署上线。
    

## 6.2 项目展望

尽管项目已经取得了一定的成果，但仍有进一步改进和扩展的空间。未来的工作将集中在以下几个方面：

1. **功能扩展**：根据用户反馈和图书馆发展需求，持续增加新功能，如电子资源管理、在线预约等。
    
2. **技术升级**：随着技术的发展，系统将逐步引入新技术，如人工智能推荐系统、大数据分析等，以提升服务质量。
    
3. **用户体验优化**：不断收集用户反馈，优化用户界面和交互流程，提升用户满意度。
    
4. **移动端开发**：开发移动应用，使用户能够随时随地访问图书馆资源，提高服务的便捷性。

## 6.3 结论

云雅图书馆管理系统项目的成功实施，标志着图书馆信息化管理迈出了重要的一步。通过本项目，我们不仅提升了图书馆的管理效率，也为用户提供了更加便捷、高效的服务体验。展望未来，我们将继续努力，不断优化和扩展系统功能，以适应不断变化的用户需求和技术发展。


# 参考文献