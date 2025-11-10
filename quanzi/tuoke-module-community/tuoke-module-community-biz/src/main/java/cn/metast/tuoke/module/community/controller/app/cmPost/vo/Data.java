package cn.metast.tuoke.module.community.controller.app.cmPost.vo;

public class Data {
        private KeyWord first;
        private KeyWord keyword1;
        private KeyWord keyword2;
        private KeyWord remark;

        public Data() {
        }

        public Data(KeyWord first, KeyWord keyword1, KeyWord keyword2, KeyWord remark) {
            this.first = first;
            this.keyword1 = keyword1;
            this.keyword2 = keyword2;
            this.remark = remark;
        }

        // 所有字段的getter和setter
        public KeyWord getFirst() {
            return first;
        }

        public void setFirst(KeyWord first) {
            this.first = first;
        }

        public KeyWord getKeyword1() {
            return keyword1;
        }

        public void setKeyword1(KeyWord keyword1) {
            this.keyword1 = keyword1;
        }

        public KeyWord getKeyword2() {
            return keyword2;
        }

        public void setKeyword2(KeyWord keyword2) {
            this.keyword2 = keyword2;
        }

        public KeyWord getRemark() {
            return remark;
        }

        public void setRemark(KeyWord remark) {
            this.remark = remark;
        }
}
