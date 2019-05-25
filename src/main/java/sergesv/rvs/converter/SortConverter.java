package sergesv.rvs.converter;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import static sergesv.rvs.util.SortUtil.getSort;

@Component
@ConfigurationPropertiesBinding
public class SortConverter implements Converter<String, Sort> {
    @Override
    public Sort convert(String source) {
        return getSort(source).orElseThrow();
    }
}
