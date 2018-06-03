笔记本
====
1.基础功能
---
	1.1显示时间戳（这里可以显示具体的记录时间）
	
![aa](https://github.com/xieyueyin/myNote/blob/master/tupian/1.png)
![aa](https://github.com/xieyueyin/myNote/blob/master/tupian/5.jpg)
<br>


修改数据库的数据信息，添加一个修改时间的long字段
添加了一个TextView显示时间戳
	
	1.2搜索功能
![aa](https://github.com/xieyueyin/myNote/blob/master/tupian/6.jpg)
<br>
	
增加了搜索功能，可根据title搜索
使用了toolbar + searchview + recyclerview 实现搜索功能
	
	
2.附加功能
----
	2.1美化UI
	
	2.2修改背景图片的颜色
![](https://github.com/xieyueyin/myNote/blob/master/tupian/3.jpg)
![](https://github.com/xieyueyin/myNote/blob/master/tupian/4.jpg)


使用了DialogFragment + 横向的ReclcyerView布局
	
	2.3排序
![](https://github.com/xieyueyin/myNote/blob/master/tupian/6.jpg)

增加了排序的功能，可以使用时间排序，对记录的笔记进行一个时间轴的排序，使了ReclcyerView + spinner
