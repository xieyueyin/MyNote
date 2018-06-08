笔记本
====
1.基础功能
---
	1.1显示时间戳（这里可以显示具体的记录时间）
	
![aa](https://github.com/xieyueyin/myNote/blob/master/tupian/1.png)
![aa](https://github.com/xieyueyin/myNote/blob/master/tupian/5.jpg)
<br>

时间戳的显示可以通过修改数据库的数据信息，然后添加一个修改时间的long字段，这样可以看到在运行截图的页面内的一些时间戳有显示出来。
添加了一个TextView显示时间戳。
	
	1.2搜索功能
![aa](https://github.com/xieyueyin/myNote/blob/master/tupian/6.jpg)
<br>
	
可以为自己的note增加搜索功能，也就是可以根据title进行note的搜
使用使用了toolbar + searchview + recyclerview 实现搜索功能
	
	
2.附加功能3
----
	2.1美化UI
	
	2.2修改背景图片的颜色
![](https://github.com/xieyueyin/myNote/blob/master/tupian/3.jpg)
![](https://github.com/xieyueyin/myNote/blob/master/tupian/4.jpg)


使用了DialogFragment + 横向的ReclcyerView布局，对ui界面进行美化，可以对note的背景选择更换颜色，用户可自由选择自己所喜欢的颜色来更换默认的颜色。
	
	2.3排序
![](https://github.com/xieyueyin/myNote/blob/master/tupian/6.jpg)

增加了排序的功能，可以使用时间排序，对记录的笔记进行一个时间轴的排序，如上图，可以看到页面内的note的记录根据了时间轴的顺序来排列，使了ReclcyerView + spinner
