function grid(divId){
	var data1 = [
   { no : 1 , name :'aaa', age : 12, gender : 'M' , english : 76.5 , math :92 },
   { no : 2 , name :'bbb', age : 11, gender : 'F' , english : 89   , math :87 },
   { no : 3 , name :'ccc', age : 13, gender : 'F' , english : 53   , math :62 }
];

var dsConfig= {
	data : data1 ,

	fields :[
		{name : 'no'     ,  type: 'int'    },
		{name : 'name'      },
		{name : 'age'    ,  type: 'int'    },
		{name : 'gender'    },
		{name : 'english',  type: 'float'  },
		{name : 'math'   ,  type: 'float'  },
		{name : 'total'   , type: 'float',
			initValue : function(record){
				return record['english'] + record['math'];
			}
		}
	]
};

var colsConfig = [
		{ id : 'no'      , header : "学号" , width : 50},
		{ id : 'name'    , header : "姓名" , width : 100},
		{ id : 'age'     , header : "年龄" , width : 50},
		{ id : 'gender'  , header : "性别" , width : 50 ,
			renderer : GT.Grid.mappingRenderer(  {'U': '未知' , 'M':'男', 'F':'女'} , '未知' )
		},
		{ id : 'english' , header : "英语" , width : 60 , align :'right' },
		{ id : 'math'    , header : "数学" , width : 60 , align :'right' },
		{ id : 'total'   , header : "总成绩" , width : 70 , align :'right' ,
			renderer : function(value ,record,columnObj,grid,colNo,rowNo){
				   var total = record['total'];
				   if (total>170) {
						total =  '<span style="color:red" >'+ total +'</span>';
				   }else if (total<120) {
						total = '<span style="color:blue" >'+ total +'</span>';
				   }
				   return total;
			}
		},

		{ id : 'detail'   , header : "详细信息" , width : 120,
			renderer : function(value ,record,columnObj,grid,colNo,rowNo){
				return '&#160;<a href=".?no='+record['no']+'" style="margin-left:3px;" >&#160;'+ record['name'] +' 的详细信息&#160;</a>';
			}
		}
];

var gridConfig={

	id : "grid1",
	
	dataset : dsConfig ,

	columns : colsConfig ,

	container : divId, 

	toolbarPosition : 'bottom',

	toolbarContent : 'state'

};

var mygrid=new GT.Grid( gridConfig );
mygrid.render();
/*
 * 利用下面注释的方法，不能利用在引用页面调用方法的形式显示表格
 * GT.Utils.onLoad( function(){
 *	mygrid.render();
 *} );
 */
 

}
