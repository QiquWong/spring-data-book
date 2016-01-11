package com.oreilly.springdata.jdbc.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAddress is a Querydsl query type for QAddress
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAddress extends com.mysema.query.sql.RelationalPathBase<QAddress> {

    private static final long serialVersionUID = 207732776;

    public static final QAddress address = new QAddress("ADDRESS");

    public final StringPath city = createString("CITY");

    public final StringPath country = createString("COUNTRY");

    public final NumberPath<Integer> customerId = createNumber("CUSTOMER_ID", Integer.class);

    public final NumberPath<Integer> id = createNumber("ID", Integer.class);

    public final StringPath street = createString("STREET");

    public final com.mysema.query.sql.PrimaryKey<QAddress> primary = createPrimaryKey(id);

    public QAddress(String variable) {
        super(QAddress.class, forVariable(variable), "null", "ADDRESS");
    }

    @SuppressWarnings("all")
    public QAddress(Path<? extends QAddress> path) {
        super((Class)path.getType(), path.getMetadata(), "null", "ADDRESS");
    }

    public QAddress(PathMetadata<?> metadata) {
        super(QAddress.class, metadata, "null", "ADDRESS");
    }

}

