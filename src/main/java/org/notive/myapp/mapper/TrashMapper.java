package org.notive.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.notive.myapp.domain.TrashVO;

public interface TrashMapper {
	
	public abstract TrashVO selectTrashByOriginNo(@Param("user_id") String user_id, @Param("origin_no") Integer origin_no);
	public abstract TrashVO selectTrash(@Param("user_id") String user_id, @Param("trash_no") Integer trash_no);
	public abstract List<TrashVO> selectTrashList(String user_id);
	public abstract Integer insertTrash(TrashVO trash);
	public abstract Integer deleteTrash(TrashVO trash);
	public abstract Integer deleteTrashByOriginNo(@Param("sort_code")Integer sort_code, @Param("origin_no")Integer integer);

}
