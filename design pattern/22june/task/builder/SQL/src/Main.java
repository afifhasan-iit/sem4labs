class Sql{
    String sql;
    String select ;
    String from ;
    String where ;
    String orWhere ;

    private Sql(SqlBuilder sqlBuilder){
        this.select = sqlBuilder.select;
        this.from = sqlBuilder.from;
        this.where = sqlBuilder.where;
        this.orWhere = sqlBuilder.orWhere;

        sql = "select " + select + " from " + from + " where " + where;

        if(orWhere != null){
            sql = sql.concat(" or " + orWhere);
        }

    }

    public static class SqlBuilder{
        private String select;
        private String from;
        private String where;
        private String orWhere;




        public SqlBuilder addSelect(String select){
            this.select = select;
            return this;
        }
        public SqlBuilder addFrom(String from){
            this.from = from;
            return this;
        }
        public SqlBuilder addWhere(String where){
            this.where = where;
            return this;
        }

        public SqlBuilder addOrwhere(String where){
            this.where += " "+where;
            return this;
        }


        public Sql build(){
            return new Sql(this);
        }

    }


}

public class Main {
    public static void main(String[] args) {

         Sql sql = new Sql.SqlBuilder()
                 .addSelect("name")
                 .addFrom("Student")
                 .addWhere("roll=10")
//                 .addOrwhere("or age=10")
                 .addOrwhere("or age=12")
                 .build();

        System.out.println(sql.sql);
    }
}



