Project Task List：
1.Parse html.
2.Create index.
3.Query.
4.Build a j2ee website.

任务描述：
使用larbin爬取网页，larbin会生成一个index维护网页和url的关系。将爬取下来的网页进行清洗，分词建索引，提供搜索接口。

任务清单：
1.解析html：主要是网页去重、去噪，提取网页内容；
2.建索引：对网页url、标题和内容进行索引，这部分任务不是很多，可能需要设置一下boost因子，可以和解析html合成一块；
3.搜索：包含对查询词的处理，修改检索模型（尚未完全搞懂lucene的检索模型）；
4.创建j2ee网站，提供用户界面。