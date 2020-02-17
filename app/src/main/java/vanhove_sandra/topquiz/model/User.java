package vanhove_sandra.topquiz.model;

    public class User {
        private String mFirstName;

        public String getMFirstName() {
            return mFirstName;
        }

        public void setMFirstName(String mFirstName) {
            this.mFirstName = mFirstName;
        }

        @Override
        public String toString() {
            return "User{" +
                    "mFirstName='" + mFirstName + '\'' +
                    '}';
        }
    }
