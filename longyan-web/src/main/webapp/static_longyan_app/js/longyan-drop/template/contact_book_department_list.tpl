
<# if(data.totalEmpNumber){ #>
<div class="total">集团总人数 <#=data.totalEmpNumber#></div>
 <# } #>

        <# if(data.departments.length > 0){ #>
            <ul>
            <# for(var i = 0; i< data.departments.length; i++){
                var departments = data.departments[i];
            #>
                  <li>
                       <div class="item" data-departmentId="<#=departments.departmentCode#>">
                           <span class="name">
                               <i class="triangle"/>
                               <#=departments.name#></span>
                           <span class="number"><#=departments.number#></span>
                       </div>
                  </li>
             <#}#>
            </ul>
        <# } #>

        <# if(data.employees){ #>
            <ul>

                <# for(var i = 0; i< data.employees.length; i++){
                    var employees = data.employees[i];
                #>
                    <li class="employee">
                          <a href="#contact_detail/<# =employees.employeeCode#>">
                              <label class="name"><# =employees.xingMing#></label>
                              <span class="job"><#=employees.head#></span>
                              <span class="number"></span>
                          </a>
                    </li>
                <# } #>
            </ul>
        <# } #>
     </div>
