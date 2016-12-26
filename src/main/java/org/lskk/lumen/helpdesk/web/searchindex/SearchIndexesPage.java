package org.lskk.lumen.helpdesk.web.searchindex;

import com.google.common.collect.ImmutableList;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.table.BootstrapDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.lskk.lumen.helpdesk.searchindex.SearchIndex;
import org.lskk.lumen.helpdesk.searchindex.SearchIndexRepository;
import org.lskk.lumen.helpdesk.web.UserLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.wicketstuff.annotation.mount.MountPath;

import javax.inject.Inject;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.Map;

@MountPath("search_indexes")
public class SearchIndexesPage extends UserLayout {

    private static final Logger log = LoggerFactory.getLogger(SearchIndexesPage.class);

    @Inject
    private SearchIndexRepository searchIndexRepo;
    @Inject
    private ElasticsearchOperations esTemplate;

    public SearchIndexesPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final ImmutableList<PropertyColumn<SearchIndex, String>> columns = ImmutableList.of(
                new PropertyColumn<SearchIndex, String>(new Model<>("ID"), "id"),
                new PropertyColumn<SearchIndex, String>(new Model<>("Created"), "creationTime"),
                new PropertyColumn<SearchIndex, String>(new Model<>("Properties"), "properties")
        );
        final int rowsPerPage = 20;
        final SortableDataProvider<SearchIndex, String> searchIndexDp = new SortableDataProvider<SearchIndex, String>() {
            @Override
            public Iterator<? extends SearchIndex> iterator(long first, long count) {
                final Page<SearchIndex> page = searchIndexRepo.findAll(
                        new PageRequest((int) first / rowsPerPage, (int) rowsPerPage,
                                Sort.Direction.ASC, "id"));
                page.forEach(it -> {
                    final Map setting = esTemplate.getSetting(it.getId());
                    log.info("Setting for {}: {}", it.getId(), setting);
                    final long indexCreationDate = Long.parseLong((String) setting.get("index.creation_date"));
                    it.setCreationTime(OffsetDateTime.ofInstant(Instant.ofEpochMilli(indexCreationDate), ZoneId.of("Asia/Jakarta")));

                    final Map core2 = esTemplate.getMapping(it.getId(), "core2");
                    log.info("Mapping core2 for {}: {}", it.getId(), core2);
                    final Map<String, Object> properties = (Map<String, Object>) core2.get("properties");
                    it.setProperties(ImmutableList.copyOf(properties.keySet()));
                });

                return page.iterator();
            }

            @Override
            public long size() {
                return searchIndexRepo.count();
            }

            @Override
            public IModel<SearchIndex> model(SearchIndex object) {
                return new Model<>(object);
            }
        };
        add(new BootstrapDefaultDataTable<>("searchIndexTable", columns, searchIndexDp, rowsPerPage));
    }

    @Override
    public IModel<String> getTitleModel() {
        return new Model<>("Indexes | Lumen Helpdesk");
    }

}
