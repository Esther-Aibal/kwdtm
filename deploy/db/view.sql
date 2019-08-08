--开放给迈安
mysql创建视图：
create view wdt_v_task_project_rel as 
select a.id task_id,a.name task_name,a.task_create_user,b.project_id,b.name project_name from wdt_t_task a,wdt_t_task_project b 
where a.id=b.task_id and b.project_id!='' order by task_id

mysql创建用户与授权：
CREATE USER 'jzywdt'@'192.168.188.129' IDENTIFIED BY 'Movit-tech'; 
GRANT SELECT ON wdt.wdt_v_task_project_rel TO 'jzywdt'@'192.168.188.129' identified by 'Movit-tech';
grant all privileges on wdt.wdt_v_task_project_rel to 'jzywdt' @'%' identified by 'Movit-tech';
flush privileges;


--CR功能数据初始化
alter table wdt_t_operate_log add loge_type varchar(10) DEFAULT NULL COMMENT '日志类型' ;
alter table wdt_t_task_message modify column message_type varchar(64) comment '消息类型（枚举维护：0：预警提醒（按预警时间提醒） 1：催办提醒 2：进度提醒（按主任务汇报频率）3：逾期提醒  4：新任务提醒 5：任务进度反馈 6：节点进度汇报 7：反馈回复 8：汇报回复   9:新增催办建议 10催办建议回复  11：任务进度反馈@ 12：节点进度汇报@ 13：反馈回复@ 14：汇报回复@   ）';
alter table wdt_t_task_comments modify column type varchar(10) comment '类型（枚举维护： 0：主任务进度反馈 1：主任务评论 2：节点进度汇报 3：节点评论   4：催办建议）';

create table wdt_t_task_comments_person
(
   id                   varchar(64) not null comment '主键',
   coments_id           varchar(64) comment '进度反馈与进度汇报表主键',
   user_id              varchar(64) comment '人员id',
   create_by            varchar(64) comment '创建人',
   create_date          datetime comment '创建时间',
   update_by            varchar(64) comment '修改人',
   update_date          datetime comment '修改时间',
   del_flag             char(1) comment '删除标记（枚举维护 0-未删除 1-已删除）',
   primary key (id)
);
alter table wdt_t_task_comments_person comment 'WDT_T_TASK_COMMENTS_PERSON-进度反馈与进度汇报';

update wdt_t_task_item set infact_completion_time=required_completion_time where execution_status in(7,8,9,10) and infact_completion_time is  null

update wdt_t_operate_log set loge_type=22 where content='完成节点'


create table wdt_t_item_report
(
   id                   varchar(64) not null comment '主键',
   task_id              varchar(64) comment '任务主键',
   item_name            varchar(200) comment '节点主键',
   report_type          int comment '汇报类型（0：状态为延期中的汇报 1：状态为进行中的汇报）',
   report_comment       varchar(10) comment '汇报情况（0：未汇报 1：延期汇报 2：正常汇报）',
   fqcy                 varchar(10) comment '汇报频率（枚举维护：0：每日 1：每周 2：每月 3：每隔天）',
   days                 int comment '天数',
   create_by            varchar(64) comment '创建人',
   create_date          datetime comment '创建时间',
   update_by            varchar(64) comment '修改人',
   update_date          datetime comment '修改时间',
   del_flag             char(1) comment '删除标记（枚举维护 0-未删除 1-已删除）',
   primary key (id)
);
alter table wdt_t_item_report comment 'WDT_T_ITEM_REPORT-节点进度汇报情况表（延迟率报表）';


INSERT INTO `sys_dict` VALUES ('100', '90d9bcb3254e42ab8fa8bce52ef5349e', '业务管理员', 'bus_admin', '角色类型', '10', '0', '1', '2017-10-27 08:00:00', '1', '2017-10-27 08:00:00', null, '0');


--CR2功能表结构修改
alter table wdt_t_task_reponse add content_char text DEFAULT NULL COMMENT '回复内容(只含文字)' ;
alter table wdt_t_task_theme add is_total_approve varchar(10) DEFAULT NULL COMMENT '是否需要统计和审批（0：需要 1：不需要）' ;
update wdt_t_task_theme set is_total_approve='0'
alter table wdt_t_task_message add content text DEFAULT NULL COMMENT '回复内容' ;