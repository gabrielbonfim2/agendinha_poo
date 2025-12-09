module org.example.projeto_poo2_agendinha {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    // --- OBRIGATÓRIOS PARA BANCO DE DADOS ---
    requires java.sql;
    requires org.postgresql.jdbc;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    requires static lombok;

    // --- PERMISSÕES ---
    opens controller to javafx.fxml;
    opens org.model to org.hibernate.orm.core, javafx.base;

    exports org;
}
