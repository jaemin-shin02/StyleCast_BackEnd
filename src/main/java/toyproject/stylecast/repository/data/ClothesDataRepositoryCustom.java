package toyproject.stylecast.repository.data;

import toyproject.stylecast.domain.clothes.*;

import java.util.List;

public interface ClothesDataRepositoryCustom {
    List<Long> SelectByTop(Top top);
    List<Long> SelectByPants(Pants pants);
    List<Long> SelectBySkirt(Skirt skirt);
    List<Long> SelectByOnepiece(Onepiece onepiece);
    List<Long> SelectByShoes(Shoes shoes);
    List<Long> SelectByOuter(Outwear outwear);
}
