function getClientTestData3(max){
		max=max||100;
		var getInt=function(min,max) {
				min = min || 0;
				max = max || 100;
				var _min= Math.min(min,max);
				max=Math.max(min,max);
				min=_min;
				var step= max - min +1;
				var s= (Math.random()+1)*max*10 % step;
				var num= min +  s ;
				return parseInt(num,10);
		}

		var data=[];
		var gl=['U','M','F'];
		for (var i=0;i<max;i++){
			var record={ no : 30+i , 
					name : 'abc'+i , 
					age :getInt(12,15) , 
					gender : gl[getInt(1,9)%3] , 
					english : 50+getInt(1,49), 
					math : 40+getInt(1,59)
				}
			data.push(record);
		}
		return data;	
	}
	function getClientTestData4(max){
		max=max||100;
		var getInt=function(min,max) {
				min = min || 0;
				max = max || 100;
				var _min= Math.min(min,max);
				max=Math.max(min,max);
				min=_min;
				var step= max - min +1;
				var s= (Math.random()+1)*max*10 % step;
				var num= min +  s ;
				return parseInt(num,10);
		}

		var data=[];
		var gl=['U','M','F'];
		for (var i=0;i<max;i++){
			var record={ no : 30+i , 
					name : 'abc'+i , 
					age :getInt(12,15) , 
					gender : gl[getInt(1,9)%3] , 
					english : 50+getInt(1,49), 
					math : 40+getInt(1,59)
				}
			data.push(record);
		}
		return data;	
	}
function grid3(grid_container){
	var data1 = getClientTestData3( 100 );

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
	],

	pageInfo : {
		pageNum : 2
	}
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

	container : grid_container, 

	toolbarPosition : 'bottom',

	toolbarContent : 'nav | goto | pagesize | reload | state' ,

	pageSize : 10 ,
	
	pageSizeList : [5,10,15,20],
	
	onDblClickCell : function(){alert(1)}
};

var mygrid3=new GT.Grid( gridConfig );
mygrid3.render();
mygrid3.gotoPage(5);
}

function grid4(grid_container){
	
var data1 = getClientTestData4( 100 );

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
			editor : { type :'select' ,options : {'U': '未知' , 'M':'男', 'F':'女'}  },
			renderer : GT.Grid.mappingRenderer(  {'U': '未知' , 'M':'男', 'F':'女'} , '未知' )
		},
		{ id : 'english' , header : "英语" , width : 60 , align :'right' , 	editor: { type :'text' ,validRule : 'R,integer' }  },
		{ id : 'math'    , header : "数学" , width : 60 , align :'right' , 	
			editor: { type :'text' ,
			
				validator : function(value,record,colObj,grid){ 

					// 将输入的数学成绩转换成数值形.
					value=Number(value);

					//取得英语成绩,并转换成数值形.
					var english = Number(record['english']);
					
					// 做校验
					if ( !isNaN(value) && ( value>10 && value< 100 ) && ( english-value <= 30 ) )   {
						return true;
					}
					return " 数学成绩 必须满足下列条件:\n"+
							" 1 输入的必须是 10到100之间(开区间)的整数.\n"+
							" 2 数学成绩 不能比 英语成绩 少30分以上.";
				
				} 
			}  
		},
		
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

	container : grid_container, 

	toolbarPosition : 'bottom',

	toolbarContent : 'nav | goto | pagesize | reload | state' ,

	pageSize : 10 ,
	
	pageSizeList : [5,10,15,20]

};

var mygrid4=new GT.Grid( gridConfig );
	mygrid4.render();
}