--4表domain初始化
update xiwa_crm_app_news a
inner join `xiwa_crm_enterprise_user` b on a.`belongedId`=b.`id`
set a.domain=b.domain;
update xiwa_sns_knowledge a
inner join `xiwa_crm_enterprise_user` b on a.`belongedId`=b.`id`
set a.domain=b.domain;
update xiwa_sns_collaboration a
inner join `xiwa_crm_enterprise_user` b on a.`belongedId`=b.`id`
set a.domain=b.domain;
update xiwa_sns_group a
inner join `xiwa_crm_enterprise_user` b on a.`belongedId`=b.`id`
set a.domain=b.domain;

update xiwa_crm_app_news set newsCategoryId = 0;