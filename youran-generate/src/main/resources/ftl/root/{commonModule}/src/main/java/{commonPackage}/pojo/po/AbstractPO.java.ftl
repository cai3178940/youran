<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("java.util.Date")/>
<@call this.printClassCom("抽象PO")/>
public abstract class AbstractPO{

    public void preInsert(String createdBy){
        Date now=new Date();
        if(this instanceof Deleted){
            ((Deleted)this).setDeleted(0);
        }
        if(this instanceof CreatedTime){
            ((CreatedTime)this).setCreatedTime(now);
        }
        if(this instanceof OperatedTime){
            ((OperatedTime)this).setOperatedTime(now);
        }
        if(this instanceof CreatedBy){
            ((CreatedBy)this).setCreatedBy(createdBy);
        }
        if(this instanceof OperatedBy){
            ((OperatedBy)this).setOperatedBy(createdBy);
        }
        if(this instanceof Version){
            ((Version)this).setVersion(1);
        }
    }

    public void preUpdate(String operatedBy){
        if(this instanceof OperatedTime){
            ((OperatedTime)this).setOperatedTime(new Date());
        }
        if(this instanceof OperatedBy){
            ((OperatedBy)this).setOperatedBy(operatedBy);
        }
    }
}
</#assign>
<#--开始渲染代码-->
package ${this.commonPackage}.pojo.po;

<@call this.printImport()/>

${code}
