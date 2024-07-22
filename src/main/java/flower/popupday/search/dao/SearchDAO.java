package flower.popupday.search.dao;

import flower.popupday.search.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface SearchDAO {
    List<SearchDTO> searchHashTags(String query) throws DataAccessException;
}
