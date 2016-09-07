<link rel="stylesheet "href="http://t.longyan.com:1000/static_longyan_app/css/contactBook.css" />


<div class="contact_detail" id="contactDetail">
    <section class="contact_detail_header" id="contactDetailHeader">
    <i class="icon iconfont icon-lyleftarrow" id="detailGoBack"></i>
    <#if(data.xingMing){#>
       <label><#=data.xingMing#></label>
       <span class="label_txt">id:<#=data.employeeCode#></span>
     <#}#>

    </section>
    <section class="contact_detail_info_list" id="contactDetailInfoList">
        <ul>
        <#if(data.businessUnitName){#>
            <li>
                <label>公司</label>
                <span class="label_txt"><#=data.businessUnitName#><span>
            </li>
        <#}#>
        <#if(data.departmentName){#>
             <li>
                 <label>部门</label>
                 <span class="label_txt"><#=data.departmentName#><span>
             </li>
        <#}#>
        <#if(data.head){#>
               <li>
                    <label>职务</label>
                    <span class="label_txt"><#=data.head#><span>
               </li>
        <#}#>
        <#if(data.mobilePhone){#>
               <li class="label_handle">
                    <label>手机</label>
                    <span class="label_txt"><a href="tel:<#=data.mobilePhone#>"><#=data.mobilePhone#></a><span>
               </li>
        <#}#>
        <#if(data.email){#>
               <li class="label_handle">
                    <label>E-mail</label>
                    <span class="label_txt"><#=data.email#><span>
               </li>
        <#}#>
        </ul>
    </section>

<div>
