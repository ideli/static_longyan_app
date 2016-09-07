
<ul>
    <# if(data.length > 0){ #>
        <# for(var i=0; i<data.length;i++){ #>
            <li class="employee">
                <a href="#contact_detail/<#=data[i].employeeCode#>">
                    <label class="name"><#=data[i].xingMing#></label>
                    <span class="job"><#=data[i].head#></span>
                    <span class="number"></span>
                 </a>
            </li>
        <# } #>
    <# }else{ #>
        <div class="nofind">
            找不到您想要的结果
        </div>
    <# } #>
</ul>
