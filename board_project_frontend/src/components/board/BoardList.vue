<template>
  <main>
    <el-row>
      <el-row class="content" type="flex" justify="center">
        <el-col :span="16">
          <el-table :data="tableData">
            <el-table-column prop="boardId" label="번호" width="100px"></el-table-column>
            <el-table-column prop="title" label="제목" width="461px">
              <template slot-scope="scope">
                <a class="custom-link" @click="boardDetail(scope.row.boardId)">{{ scope.row.title }}</a>
              </template>
            </el-table-column>
            <el-table-column prop="nickname" label="작성자" width="200px"></el-table-column>
            <el-table-column prop="date" label="날짜" width="170px">
              <template slot-scope="scope">
                {{ scope.row.date | formatDate }}
              </template>
            </el-table-column>
            <el-table-column prop="viewCount" label="조회수" width="100px"></el-table-column>
          </el-table>
        </el-col>
      </el-row>

      <el-row class="icon-area" type="flex" justify="center">
        <el-col :span="16">
          <router-link to="/boardform">
            <el-button type="primary" icon="el-icon-document" size="small" plain>글작성</el-button>
          </router-link>
        </el-col>
      </el-row>
    </el-row>
  </main>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
    };
  },
  mounted() {
    // 게시글 리스트 불러오기
    this.$http.get("/api/board")
      .then(response => {
        this.tableData = response.data.sort((a, b) => {
          return b.boardId - a.boardId;
        });
      })
      .catch(error => {
        alert("게시판 리스트를 가져오는 중 오류가 발생했습니다.")
        console.error("게시판 리스트를 가져오는 중 오류가 발생했습니다.", error);
      });
  },
  methods: {
    boardDetail(boardId) {
      // 글 번호(boardId)를 사용하여 글 조회 페이지로 이동
      this.$router.push("/boarddetail/" + boardId);
    },
  },
  filters: {
    formatDate(date) {
      // JavaScript Date 객체로 변환
      const jsDate = new Date(date.replace("T", " "));
      
      // 원하는 형식으로 날짜 포맷팅 (AM/PM 제거)
      const year = jsDate.getFullYear();
      const month = String(jsDate.getMonth() + 1).padStart(2, "0");
      const day = String(jsDate.getDate()).padStart(2, "0");
      const hours = String(jsDate.getHours()).padStart(2, "0");
      const minutes = String(jsDate.getMinutes()).padStart(2, "0");
              
      // 최종 형식으로 조합
      return `${year}-${month}-${day} ${hours}:${minutes}`;
    }
  }
}
</script>

<style scoped>
  .content {
    margin-top: 2%;
  }
  .custom-link {
    text-decoration: none; /* 링크 밑줄 제거 */
    border-radius: 5px;
    cursor: pointer; /* 마우스 커서를 포인터로 변경 */
  }
  .icon-area {
    margin-top: 2%;
    text-align: right;
  }
</style>