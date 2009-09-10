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
            		{name : 'math'   ,  type: 'float'  }
            	]
            };

            var colsConfig = [
            		{ id : 'no'      , header : "学号" },
            		{ id : 'name'    , header : "姓名" },
            		{ id : 'age'     , header : "年龄" },
            		{ id : 'gender'  , header : "性别" },
            		{ id : 'english' , header : "英语" },
            		{ id : 'math'    , header : "数学" }
            ];

            var gridConfig={

            	id : "grid1",
            	
            	dataset : dsConfig ,

            	columns : colsConfig ,

            	container : 'grid1_container', 

            	toolbarPosition : 'bottom',

            	toolbarContent : 'state'

            };

            var mygrid=new GT.Grid( gridConfig );

            GT.Utils.onLoad( function(){
            	mygrid.render();
            } );