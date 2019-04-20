<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <style>
 	a{cursor: pointer;}
</style>
   
    <input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum }"/>
    <input type="hidden" id="pageCount" name="pageCount" value="${page.pageCount }"/>
    <c:set var="startPage" value="${page.pageNum-page.pageNum%5+1 }"/>
    
    <c:if test="${page.pageNum%5==0 }">
        <c:set var="startPage" value="${startPage-5 }"/>
    </c:if>
    <c:set var="endPage" value="${startPage+4 }"/>
    <c:if test="${page.pageCount-startPage < 5 }">
        <c:set var="endPage" value="${page.pageCount }"/>
    </c:if>
    <nav>
		<ul class="pagination pagination-sm">
			<li id="prvPage" onclick="goPage(1,this.id)"><a title="首页">&laquo;</a></li>
            <li id="last" class="<c:if test="${page.pageNum == 1 }">disable</c:if>" onclick="lastPage(this.id)"><a>上一页</a></li>
            <c:choose>
               <c:when test="${page.pageCount > 5 }">
                    <c:forEach var="startIndex" begin="${startPage }" end="${endPage }" step="1">
                        <li id="current" <c:if test="${page.pageNum == startIndex }">class="active"</c:if> onclick="goPage(${startIndex },this.id)">
                        	<a>${startIndex }</a>
                        </li>
                    </c:forEach>
               </c:when>
               <c:otherwise>
                    <c:forEach var="i" begin="1" end="${page.pageCount }" step="1">
                        <li id="current" <c:if test="${page.pageNum == i }">class="active"</c:if> onclick="goPage(${i },this.id)">
                        <a>${i }</a></li>
                    </c:forEach>
               </c:otherwise>
            </c:choose>
	
		<li><a>...</a></li>
		<li><a>共${page.pageCount }页</a></li>
		<li id="next" class="<c:if test="${page.pageNum == page.pageCount }">disable</c:if>" onclick="nextPage(this.id)"><a>下一页</a></li>
		<li id="lastPage" onclick="goPage(${page.pageCount},this.id)"><a  title="尾页">&raquo;</a></li>
		<span>到</span>
		<li>
			<input type="text" class="input-medium required digits" id="targetPage" style="width: 38px;height:30px; text-align: center;" 
			  value="${page.pageNum }"  onkeyup="this.value=this.value.replace(/\D/g,'')"  onblur="checkPageNum(this)">
		</li>
		 <span>页&nbsp;&nbsp;共<strong>${page.rowCount}</strong>条</span>
		 <input id="go" type="button" value="确定" class="sure btn btn-sm btn-success" onclick="goPage($('#targetPage').val(),this.id)">
		
		</ul>
	
	</nav>
<script type="text/javascript">
  /**
   *此分页必须要有表单有有一个Id,而且class=form-search.form表单必须包括分页common-pagination.jsp
   */
   /**执行第一页**/
    function setStart(){
    	 $('#pageNum').val(1);
    }
    /**检查这个页数**/
    function checkPageNum(obj){
    	var pageCount = $('#pageCount').val();
    	if(obj.value > parseInt(pageCount)){
    		//如果大于当前页数的时候就变成btn白色.
    		$('#go').attr('class', 'sure btn-sm disable');
    	}else{
    		$('#go').attr('class', 'btn btn-sm');
    	}
    }
    /**执行分页**/
    function goPage(pageNum,obj){
    	var formId = $('#' + obj).parents(".form-search").attr('id');
    	var pageCount = $('#pageCount').val();
    	if(parseInt(pageNum) <= parseInt(pageCount)){
    		$('#pageNum').val(pageNum);
            $('#' + formId).submit();
    	}
    }
    /**执行最后一页**/
    function lastPage(obj){
    	var formId = $('#' + obj).parents(".form-search").attr('id');
    	var pageNum = $('#pageNum').val();
        var lastPageNum = parseInt(pageNum) - 1;
        if(lastPageNum != 0){
        	$('#pageNum').val(lastPageNum);
            $('#' + formId).submit();
        }
    }
    /**执行下一页**/
    function nextPage(obj){
    	var formId = $('#' + obj).parents(".form-search").attr('id');
        var pageNum = $('#pageNum').val();
        var pageCount = $('#pageCount').val();
        var nextPageNum = parseInt(pageNum) + 1;
        if(parseInt(pageNum) < parseInt(pageCount)){
        	$('#pageNum').val(nextPageNum);
            $('#' + formId).submit();
        }
    }
</script>