
ALTER TABLE tb_xa_announce COMMENT='公告表';
 alter table bszk.tb_xa_announce modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_announce modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_announce modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_announce modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_announce modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_announce modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_announce modify column status int(11) not null  default 1 comment '状态 1-有效 3-逻辑删除'; 
 alter table bszk.tb_xa_announce modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_announce modify column ayear varchar(10) not null  comment '年份';        
 alter table bszk.tb_xa_announce modify column bmonth varchar(2) not null  comment '年月月份';        
 alter table bszk.tb_xa_announce modify column byear varchar(10) not null  comment '年月年份';        
 alter table bszk.tb_xa_announce modify column month_orders double not null  comment '月订单数';      
 alter table bszk.tb_xa_announce modify column year_orders double not null  comment '年订单数';  

ALTER TABLE tb_xa_assistant COMMENT='专家助手表';
  alter table bszk.tb_xa_assistant modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_assistant modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_assistant modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_assistant modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_assistant modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_assistant modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_assistant modify column status int(11) not null  default 1 comment '状态 1-有效 3-逻辑删除'; 
 alter table bszk.tb_xa_assistant modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_assistant modify column birth datetime   comment '出身日期';                   
 alter table bszk.tb_xa_assistant modify column certificate varchar(50)   comment '证件号';          
 alter table bszk.tb_xa_assistant modify column contact varchar(200)   comment '联系方式';             
 alter table bszk.tb_xa_assistant modify column email varchar(50)   comment 'email';                
 alter table bszk.tb_xa_assistant modify column expert_id varchar(32) not null  comment '专家id';    
 alter table bszk.tb_xa_assistant modify column gender varchar(10)   comment '性别';               
 alter table bszk.tb_xa_assistant modify column name varchar(50) not null  comment '姓名';         
 alter table bszk.tb_xa_assistant modify column serial int(11) not null  comment '序号';           
 alter table bszk.tb_xa_assistant modify column wechat varchar(50)   comment '微信号';     


ALTER TABLE tb_xa_browse COMMENT='浏览记录表';
  alter table bszk.tb_xa_browse modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_browse modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_browse modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_browse modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_browse modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_browse modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_browse modify column status int(11) not null  default 1 comment '状态 1-有效 3-逻辑删除'; 
 alter table bszk.tb_xa_browse modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_browse modify column btime datetime not null  comment '浏览时间';           
 alter table bszk.tb_xa_browse modify column expert_id varchar(32) not null  comment '浏览对象ID';    
 alter table bszk.tb_xa_browse modify column user_id varchar(32) not null  comment '浏览者id';      
 alter table bszk.tb_xa_browse modify column btype int(11) not null  comment '浏览对象类型 1-专家 2-客户';     

ALTER TABLE tb_xa_contactor COMMENT='客户联系人表';
  alter table bszk.tb_xa_contactor modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_contactor modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_contactor modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_contactor modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_contactor modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_contactor modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_contactor modify column status int(11) not null  default 1 comment '状态 1-有效 3-逻辑删除'; 
 alter table bszk.tb_xa_contactor modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_contactor modify column contact varchar(200)   comment '联系方式';             
 alter table bszk.tb_xa_contactor modify column customer_id varchar(32) not null  comment '客户id';  
 alter table bszk.tb_xa_contactor modify column email varchar(50)   comment 'email';                
 alter table bszk.tb_xa_contactor modify column name varchar(50) not null  comment '姓名';         
 alter table bszk.tb_xa_contactor modify column serial int(11) not null  comment '序号';           
 alter table bszk.tb_xa_contactor modify column wechat varchar(50)   comment '微信号';     



ALTER TABLE tb_xa_cooperate COMMENT='客户合作表';
 alter table bszk.tb_xa_cooperate modify column id varchar(32) not null  comment 'id';            
 alter table bszk.tb_xa_cooperate modify column create_time varchar(255)   comment '创建时间';          
 alter table bszk.tb_xa_cooperate modify column create_user varchar(255)   comment '创建用户';          
 alter table bszk.tb_xa_cooperate modify column modify_description longtext   comment '修改描述';       
 alter table bszk.tb_xa_cooperate modify column modify_time varchar(255)   comment '修改时间';          
 alter table bszk.tb_xa_cooperate modify column modify_user varchar(255)   comment '修改用户';          
 alter table bszk.tb_xa_cooperate modify column status int(11) not null  default 1 comment '状态 1-有效 3-逻辑删除';  
 alter table bszk.tb_xa_cooperate modify column version int(11)   comment 'hibernate控制的版本号';                   
 alter table bszk.tb_xa_cooperate modify column action_address varchar(200)   comment '活动地点';       
 alter table bszk.tb_xa_cooperate modify column action_date datetime not null  comment '活动日期';      
 alter table bszk.tb_xa_cooperate modify column comment longtext   comment '评价';                  
 alter table bszk.tb_xa_cooperate modify column contract_price double not null  comment '合同价';     
 alter table bszk.tb_xa_cooperate modify column cost_price double not null  comment '成本价';         
 alter table bszk.tb_xa_cooperate modify column customer_name varchar(50) not null  comment '客户名称'; 
 alter table bszk.tb_xa_cooperate modify column expert_contactor varchar(50)   comment '专家联系人';      
 alter table bszk.tb_xa_cooperate modify column expert_id varchar(32) not null  comment '专家id';     
 alter table bszk.tb_xa_cooperate modify column pics varchar(200)   comment '活动现场图';    



ALTER TABLE tb_xa_customer COMMENT='客户表';
  alter table bszk.tb_xa_customer modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_customer modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_customer modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_customer modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_customer modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_customer modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_customer modify column status int(11) not null  default 1 comment '状态 1-有效 3-逻辑删除'; 
 alter table bszk.tb_xa_customer modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_customer modify column city varchar(10)   comment '所在市';                 
 alter table bszk.tb_xa_customer modify column comment varchar(20)   comment '备注';              
 alter table bszk.tb_xa_customer modify column contact varchar(200)   comment '联系方式';             
 alter table bszk.tb_xa_customer modify column district varchar(10)   comment '所在区 （忽略）';             
 alter table bszk.tb_xa_customer modify column email varchar(50)   comment 'email';                
 alter table bszk.tb_xa_customer modify column file varchar(200)   comment '附件路径';                
 alter table bszk.tb_xa_customer modify column intro varchar(100)   comment '简介';               
 alter table bszk.tb_xa_customer modify column manager varchar(50)   comment '负责人';              
 alter table bszk.tb_xa_customer modify column name varchar(200) not null  comment '客户名称';        
 alter table bszk.tb_xa_customer modify column province varchar(10)   comment '所在省';             
 alter table bszk.tb_xa_customer modify column service_area varchar(20)   comment '服务区域';         
 alter table bszk.tb_xa_customer modify column service_object varchar(20)   comment '服务对象';       
 alter table bszk.tb_xa_customer modify column wechat varchar(50)   comment '微信号';        



ALTER TABLE tb_xa_expert COMMENT='专家表';
alter table bszk.tb_xa_expert modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_expert modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_expert modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_expert modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_expert modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_expert modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_expert modify column status int(11) not null  default 1 comment '状态 1-有效 3-逻辑删除'; 
 alter table bszk.tb_xa_expert modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_expert modify column birth datetime   comment '出生日期';                   
 alter table bszk.tb_xa_expert modify column category longtext   comment '专家类别';                
 alter table bszk.tb_xa_expert modify column certificate varchar(200)   comment '证件号';         
 alter table bszk.tb_xa_expert modify column city varchar(10)   comment '所在市';                 
 alter table bszk.tb_xa_expert modify column comment longtext   comment '备注';                 
 alter table bszk.tb_xa_expert modify column contact varchar(100)   comment '联系方式';             
 alter table bszk.tb_xa_expert modify column district varchar(10)   comment '所在区（忽略）';             
 alter table bszk.tb_xa_expert modify column email varchar(200)   comment 'email';               
 alter table bszk.tb_xa_expert modify column file_path varchar(200)   comment '附件路径';           
 alter table bszk.tb_xa_expert modify column gender varchar(10)   comment '性别';               
 alter table bszk.tb_xa_expert modify column name varchar(50) not null  comment '姓名';         
 alter table bszk.tb_xa_expert modify column other_category varchar(200)   comment '专家类别其他';      
 alter table bszk.tb_xa_expert modify column province varchar(10)   comment '所在省';             
 alter table bszk.tb_xa_expert modify column social_function longtext   comment '社会职能';         
 alter table bszk.tb_xa_expert modify column wechat varchar(200)   comment '微信号'; 


ALTER TABLE tb_xa_expertcategory COMMENT='专家类别表';
  alter table bszk.tb_xa_expertcategory modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_expertcategory modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_expertcategory modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_expertcategory modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_expertcategory modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_expertcategory modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_expertcategory modify column status int(11) not null  default 1 comment '状态 1-有效 3-逻辑删除'; 
 alter table bszk.tb_xa_expertcategory modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_expertcategory modify column comment varchar(200)   comment '名称';             
 alter table bszk.tb_xa_expertcategory modify column name varchar(50) not null  comment '备注';


ALTER TABLE tb_xa_intro COMMENT='介绍表';
  alter table bszk.tb_xa_intro modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_intro modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_intro modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_intro modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_intro modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_intro modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_intro modify column status int(11) not null  default 1 comment '状态 1-有效 3-逻辑删除'; 
 alter table bszk.tb_xa_intro modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_intro modify column file_name varchar(50) not null  comment '文件名称';    
 alter table bszk.tb_xa_intro modify column file_path varchar(200) not null  comment '文件路径';   
 alter table bszk.tb_xa_intro modify column uptime datetime not null  comment '上传日期';     

ALTER TABLE tb_xa_lecturecontent COMMENT='演讲内容表';
alter table bszk.tb_xa_lecturecontent modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_lecturecontent modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_lecturecontent modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_lecturecontent modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_lecturecontent modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_lecturecontent modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_lecturecontent modify column status int(11) not null  default 1 comment '状态 1-有效 3-逻辑删除'; 
 alter table bszk.tb_xa_lecturecontent modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_lecturecontent modify column content longtext not null  comment '内容';         
 alter table bszk.tb_xa_lecturecontent modify column expert_id varchar(32) not null  comment '专家id';    
 alter table bszk.tb_xa_lecturecontent modify column serial int(11) not null  comment '序列号';    




 ALTER TABLE tb_xa_message COMMENT='消息表';
alter table bszk.tb_xa_message modify column id bigint(20) unsigned not null  auto_increment comment 'id'; 
alter table bszk.tb_xa_message modify column create_time varchar(255)   comment '创建日期';                      
alter table bszk.tb_xa_message modify column create_user bigint(20)   comment '创建用户';                        
alter table bszk.tb_xa_message modify column modify_description longtext   comment '修改描述';                   
alter table bszk.tb_xa_message modify column modify_time varchar(255)   comment '修改时间';                      
alter table bszk.tb_xa_message modify column modify_user bigint(20)   comment '修改用户';                        
alter table bszk.tb_xa_message modify column status int(11) not null  default 1 comment '状态 1－正常 3－逻辑删除';              
alter table bszk.tb_xa_message modify column version int(11)   comment '版本号';                               
alter table bszk.tb_xa_message modify column channel varchar(50) not null  comment '渠道';                   
alter table bszk.tb_xa_message modify column content longtext not null  comment '内容';                      
alter table bszk.tb_xa_message modify column failure_reason longtext   comment '失败原因';                       
alter table bszk.tb_xa_message modify column group_id bigint(20)   comment '组号';                           
alter table bszk.tb_xa_message modify column mobile varchar(50)   comment '手机号';                            
alter table bszk.tb_xa_message modify column mstatus int(11) not null  comment '发送状态';                       
alter table bszk.tb_xa_message modify column priority int(11) not null  comment '优先级';                      
alter table bszk.tb_xa_message modify column receiver_id bigint(20) not null  comment '接受者id';                
alter table bszk.tb_xa_message modify column receiver_type int(11) not null  comment '接受者类型';                 
alter table bszk.tb_xa_message modify column retry_times int(11)   comment '重试次数';                           
alter table bszk.tb_xa_message modify column send_date datetime   comment '发送日期';                            
alter table bszk.tb_xa_message modify column sender_id bigint(20) not null  comment '发送者id';                  
alter table bszk.tb_xa_message modify column title longtext not null  comment '标题';  

 ALTER TABLE tb_xa_news COMMENT='新闻表';
 alter table bszk.tb_xa_news modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_news modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_news modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_news modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_news modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_news modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_news modify column status int(11) not null  default 1 comment '状态 1－正常 3－逻辑删除'; 
 alter table bszk.tb_xa_news modify column version int(11)   comment '版本号';                  
 alter table bszk.tb_xa_news modify column content varchar(40) not null  comment '内容';  



 ALTER TABLE tb_xa_pics COMMENT='宣传照片表';
  alter table bszk.tb_xa_pics modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_pics modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_pics modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_pics modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_pics modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_pics modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_pics modify column status int(11) not null  default 1 comment '状态 1－正常 3－逻辑删除'; 
 alter table bszk.tb_xa_pics modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_pics modify column expert_id varchar(32) not null  comment '专家id';    
 alter table bszk.tb_xa_pics modify column pic varchar(200) not null  comment '图片';         
 alter table bszk.tb_xa_pics modify column serial int(11) not null  comment '序号';  


 ALTER TABLE tb_xa_userextend COMMENT='宣传照片表';
  alter table bszk.tb_xa_userextend modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_userextend modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_userextend modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_userextend modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_userextend modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_userextend modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_userextend modify column status int(11) not null  default 1 comment '状态 1－正常 3－逻辑删除'; 
 alter table bszk.tb_xa_userextend modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_userextend modify column company varchar(200)   comment '所在公司';             
 alter table bszk.tb_xa_userextend modify column name varchar(50) not null  comment '姓名';         
 alter table bszk.tb_xa_userextend modify column user_id varchar(32) not null  comment '用户id';      
 alter table bszk.tb_xa_userextend modify column user_type bigint(20) not null  comment '用户类型';   


 ALTER TABLE tb_xa_work COMMENT='合作表';
  alter table bszk.tb_xa_work modify column id varchar(32) not null  comment 'id';           
 alter table bszk.tb_xa_work modify column create_time varchar(255)   comment '创建时间';         
 alter table bszk.tb_xa_work modify column create_user varchar(255)   comment '创建用户';         
 alter table bszk.tb_xa_work modify column modify_description longtext   comment '修改描述';      
 alter table bszk.tb_xa_work modify column modify_time varchar(255)   comment '修改时间';         
 alter table bszk.tb_xa_work modify column modify_user varchar(255)   comment '修改用户';         
 alter table bszk.tb_xa_work modify column status int(11) not null  default 1 comment '状态 1－正常 3－逻辑删除'; 
 alter table bszk.tb_xa_work modify column version int(11)   comment 'hibernate控制的版本号';                  
 alter table bszk.tb_xa_work modify column action_address varchar(10)   comment '活动地点';       
 alter table bszk.tb_xa_work modify column action_date datetime   comment '活动日期';             
 alter table bszk.tb_xa_work modify column comment varchar(10)   comment '评价';              
 alter table bszk.tb_xa_work modify column customer_id varchar(32) not null  comment '客户id';  
 alter table bszk.tb_xa_work modify column expert_name varchar(20) not null  comment '专家姓名';  
 alter table bszk.tb_xa_work modify column linker varchar(10) not null  comment '联系人';       
 alter table bszk.tb_xa_work modify column pics varchar(200)   comment '现场图';                
 alter table bszk.tb_xa_work modify column serial int(11) not null  comment '序列号'; 




 ALTER TABLE tb_ms_cms_aboutus COMMENT='关于我们定义表';
 alter table bszk.tb_ms_cms_aboutus modify column id bigint(20) unsigned not null  auto_increment comment 'id'; 
 alter table bszk.tb_ms_cms_aboutus modify column create_time varchar(255)   comment '创建日期';                      
 alter table bszk.tb_ms_cms_aboutus modify column create_user bigint(20)   comment '创建用户';                        
 alter table bszk.tb_ms_cms_aboutus modify column modify_description longtext   comment '修改描述';                   
 alter table bszk.tb_ms_cms_aboutus modify column modify_time varchar(255)   comment '修改时间';                      
 alter table bszk.tb_ms_cms_aboutus modify column modify_user bigint(20)   comment '修改用户';                        
 alter table bszk.tb_ms_cms_aboutus modify column status int(11) not null  default 1 comment '状态 1－正常 3－逻辑删除';              
 alter table bszk.tb_ms_cms_aboutus modify column version int(11)   comment '版本号';                               
 alter table bszk.tb_ms_cms_aboutus modify column content longtext   comment '内容';                              
 alter table bszk.tb_ms_cms_aboutus modify column logo longtext   comment 'logo';                                 
 alter table bszk.tb_ms_cms_aboutus modify column pics longtext   comment '轮播图';                                 
 alter table bszk.tb_ms_cms_aboutus modify column tel varchar(50)   comment '电话';                               
 alter table bszk.tb_ms_cms_aboutus modify column versionno varchar(50)   comment '软件版本号';  

ALTER TABLE tb_ms_cms_dict COMMENT='字典表';
  alter table bszk.tb_ms_cms_dict modify column id bigint(20) not null  auto_increment comment 'id'; 
 alter table bszk.tb_ms_cms_dict modify column key varchar(20)   comment '字典key';                      
 alter table bszk.tb_ms_cms_dict modify column code varchar(50)   comment '字典代码';                     
 alter table bszk.tb_ms_cms_dict modify column value varchar(100) not null  comment '字典值';           
 alter table bszk.tb_ms_cms_dict modify column status int(11) not null  default 1 comment '状态 1－启用 0－禁用';     
 alter table bszk.tb_ms_cms_dict modify column parent_code varchar(50)   comment '父代码值';              
 alter table bszk.tb_ms_cms_dict modify column is_leaf int(11)   default 0 comment '是否叶子节点';  

ALTER TABLE tb_ms_cms_dict_meta COMMENT='字典元数据表';
  alter table bszk.tb_ms_cms_dict_meta modify column id bigint(20) not null  auto_increment comment 'id'; 
 alter table bszk.tb_ms_cms_dict_meta modify column key varchar(20) not null  comment '字典key';              
 alter table bszk.tb_ms_cms_dict_meta modify column parent_key varchar(20)   comment '父字典key';    


ALTER TABLE tb_ms_cms_feedback COMMENT='用户反馈表';
 alter table bszk.tb_ms_cms_feedback modify column id bigint(20) unsigned not null  auto_increment comment 'id'; 
 alter table bszk.tb_ms_cms_feedback modify column create_time varchar(255)   comment '创建日期';                      
 alter table bszk.tb_ms_cms_feedback modify column create_user bigint(20)   comment '创建用户';                        
 alter table bszk.tb_ms_cms_feedback modify column modify_description longtext   comment '修改描述';                   
 alter table bszk.tb_ms_cms_feedback modify column modify_time varchar(255)   comment '修改日期';                      
 alter table bszk.tb_ms_cms_feedback modify column modify_user bigint(20)   comment '修改用户';                        
 alter table bszk.tb_ms_cms_feedback modify column status int(11) not null  default 1 comment '状态 1－正常 3－逻辑删除';              
 alter table bszk.tb_ms_cms_feedback modify column version int(11)   comment '版本号';                               
 alter table bszk.tb_ms_cms_feedback modify column content longtext not null  comment '反馈内容';                      
 alter table bszk.tb_ms_cms_feedback modify column fdate datetime not null  comment '反馈日期';                        
 alter table bszk.tb_ms_cms_feedback modify column user_id bigint(20)   comment '用户id';   


ALTER TABLE tb_ms_cms_log COMMENT='日志表';
 alter table bszk.tb_ms_cms_log modify column id bigint(20) unsigned not null  auto_increment comment 'id'; 
 alter table bszk.tb_ms_cms_log modify column create_time varchar(255)   comment '创建日期';                      
 alter table bszk.tb_ms_cms_log modify column create_user bigint(20)   comment '创建用户';                        
 alter table bszk.tb_ms_cms_log modify column modify_description longtext   comment '修改描述';                   
 alter table bszk.tb_ms_cms_log modify column modify_time varchar(255)   comment '修改日期';                      
 alter table bszk.tb_ms_cms_log modify column modify_user bigint(20)   comment '修改用户';                        
 alter table bszk.tb_ms_cms_log modify column status int(11) not null  default 1 comment '状态 1－正常 3－逻辑删除';              
 alter table bszk.tb_ms_cms_log modify column version int(11)   comment '版本号';                               
 alter table bszk.tb_ms_cms_log modify column client varchar(50)   comment '客户端';                            
 alter table bszk.tb_ms_cms_log modify column ldate datetime not null  comment '日志日期';                        
 alter table bszk.tb_ms_cms_log modify column method varchar(50) not null  comment '调用方法';                    
 alter table bszk.tb_ms_cms_log modify column result longtext   comment '调用结果';                               
 alter table bszk.tb_ms_cms_log modify column service varchar(50) not null  comment '调用服务';                   
 alter table bszk.tb_ms_cms_log modify column used_times bigint(20) not null  comment '用时';                 
 alter table bszk.tb_ms_cms_log modify column user_id bigint(20)   comment '用户id';   


ALTER TABLE tb_ms_cms_resource COMMENT='资源表';
 alter table bszk.tb_ms_cms_resource modify column resource_id bigint(20) not null  auto_increment comment '资源id'; 
 alter table bszk.tb_ms_cms_resource modify column icon varchar(255)   comment '资源图标';                             
 alter table bszk.tb_ms_cms_resource modify column order_num int(11)   comment '序号';                             
 alter table bszk.tb_ms_cms_resource modify column parent_id bigint(20)   comment '父资源id';                          
 alter table bszk.tb_ms_cms_resource modify column resource_name varchar(40) not null  comment '资源名称';             
 alter table bszk.tb_ms_cms_resource modify column resource_url varchar(100) not null  comment '资源url';             
 alter table bszk.tb_ms_cms_resource modify column show_type int(11)   comment '显示类型 0-页面 1-action 2-菜单';                             
 alter table bszk.tb_ms_cms_resource modify column status int(11)   comment '状态'; 


ALTER TABLE tb_ms_cms_role COMMENT='角色表';
  alter table bszk.tb_ms_cms_role modify column role_id bigint(20) not null  auto_increment comment '角色id'; 
 alter table bszk.tb_ms_cms_role modify column create_time varchar(255)   comment '创建日期';                  
 alter table bszk.tb_ms_cms_role modify column role_desc varchar(255)   comment '角色描述';                    
 alter table bszk.tb_ms_cms_role modify column role_name varchar(40) not null  comment '角色名称';             
 alter table bszk.tb_ms_cms_role modify column status int(11)   comment '状态';                            
 alter table bszk.tb_ms_cms_role modify column update_time varchar(255)   comment '更新时间';                  
 alter table bszk.tb_ms_cms_role modify column user_id bigint(20)   comment '创建用户id'; 


ALTER TABLE tb_ms_cms_role_resource COMMENT='角色资源表';
alter table bszk.tb_ms_cms_role_resource modify column role_resource_id bigint(20) not null  auto_increment comment 'id'; 
 alter table bszk.tb_ms_cms_role_resource modify column resource_id bigint(20)   comment '资源id';                             
 alter table bszk.tb_ms_cms_role_resource modify column role_id bigint(20)   comment '角色id';                                 
 alter table bszk.tb_ms_cms_role_resource modify column version bigint(20)   comment '版本号';      


ALTER TABLE tb_ms_cms_user COMMENT='用户表';
  alter table bszk.tb_ms_cms_user modify column user_id varchar(32) not null  comment '用户id'; 
 alter table bszk.tb_ms_cms_user modify column email varchar(50)   comment '电子邮件';                         
 alter table bszk.tb_ms_cms_user modify column last_login_date varchar(50)   comment '最近登录时间';               
 alter table bszk.tb_ms_cms_user modify column mobile varchar(50)   comment '手机号';                        
 alter table bszk.tb_ms_cms_user modify column nick_name varchar(50)   comment '昵称';                     
 alter table bszk.tb_ms_cms_user modify column password varchar(50)   comment '密码';                      
 alter table bszk.tb_ms_cms_user modify column regist_date varchar(50)   comment '注册日期';                   
 alter table bszk.tb_ms_cms_user modify column remark longtext   comment '备注';                           
 alter table bszk.tb_ms_cms_user modify column role_id bigint(20)   comment '所属角色id';                        
 alter table bszk.tb_ms_cms_user modify column status int(11) not null  comment '状态';                    
 alter table bszk.tb_ms_cms_user modify column user_name varchar(50)   comment '用户名';                     
 alter table bszk.tb_ms_cms_user modify column user_type int(11)   comment '用户类型'; 