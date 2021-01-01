package fudan.se.lab2.domain;

import javax.persistence.*;
@Entity
@Table(name = "secondDiscussion")
public class SecondDiscussion {


        @Id
        @Column
        @GeneratedValue(strategy = GenerationType.AUTO)//自动生成
        private Long id;

        @Column
        private String title;

        @Column
        private String statement;

        @Column
        private String speaker;//PCmember username
         public SecondDiscussion(){}
        public SecondDiscussion(String title, String statement, String speaker) {
            this.speaker = speaker;
            this.statement = statement;
            this.title = title;

        }

        public String getTitle() {
            return title;
        }

        public String getSpeaker() {
            return speaker;
        }

        public String getStatement() {
            return statement;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSpeaker(String speaker) {
            this.speaker = speaker;
        }

        public void setStatement(String statement) {
            this.statement = statement;
        }

    }
