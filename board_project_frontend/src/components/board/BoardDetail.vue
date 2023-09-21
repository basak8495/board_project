<template>
  <main>
    <el-row>
      <el-row class="content-top" type="flex" justify="center">
        <el-col :span="18">
          <el-row>
            <span>{{ boardData.title }}</span>
          </el-row>
        </el-col>
      </el-row>

      <el-row>
        <el-row justify="center" type="flex">
          <el-col :span="18">
            <el-divider></el-divider> <!--경계선-->
          </el-col>
        </el-row>

        <el-row justify="center" type="flex">
          <el-col :span="12">
            <el-row class="date">
              <span>{{ boardData.nickname }}</span>
            </el-row>
          </el-col>

          <el-col :span="5">
            <el-col class="date">
                {{ boardData.date | formatDate }}&emsp;&emsp;&emsp;&emsp;조회수: {{ boardData.viewCount }}
            </el-col>
          </el-col>
        </el-row>
      </el-row>

      <el-row>
        <el-row justify="center" type="flex">
          <el-col :span="18">
            <el-divider></el-divider> <!--경계선-->
          </el-col>
        </el-row>

        <el-row justify="center" type="flex" class="content"> 
          <el-col :span="17">
            <span>{{ boardData.content }}</span>
          </el-col>
        </el-row>
      </el-row>
      
      <el-row>
        <el-row justify="center" type="flex">
          <el-col :span="18">
            <el-divider></el-divider> <!--경계선-->
          </el-col>
        </el-row>

        <el-row justify="center" type="flex">
          <el-col :span="14">
            <el-row class="date">
              첨부파일: <a :href="downloadLink" download="파일명.txt">파일 다운로드</a>
            </el-row>  
          </el-col>

          <el-col :span="4" class="icon-area">
            <!-- <el-button icon="el-icon-back" circle @click="back()"></el-button>  뒤로가기 버튼 -->
            <el-button type="warning" icon="el-icon-edit" size="small" plain @click="boardModify(boardData.memberId)">수정</el-button> <!-- 수정하기 버튼 -->
            <el-button type="info" icon="el-icon-delete" size="small" plain @click="boardRemove()">삭제</el-button> <!-- 삭제 버튼 -->
          </el-col>
        </el-row>
      </el-row>

      <el-row>
        <el-row justify="center" type="flex">
          <el-col :span="18">
            <el-divider></el-divider> <!--경계선-->
          </el-col>
        </el-row>

        <el-row justify="center" type="flex">
          <el-col :span="18">
            <el-row class="date">
              댓글
            </el-row>
          </el-col>
        </el-row>

        <el-row justify="center" type="flex" class="comment-section"> <!-- 댓글 영역에 추가된 클래스 -->
          <el-col :span="18">
            <el-col :span="7">
              <!-- 댓글 내용을 나타내는 반복문 -->
              <div v-for="commentData in commentData" :key="commentData.commentId" class="comment"> <!-- 댓글에 대한 스타일 추가 -->
                <el-row>
                  <el-col class="comment-details" :span="16">
                    <span>작성자: {{ commentData.nickname }}</span>
                  </el-col>
                  <el-col class="comment-details" :span="8">
                    <span>{{ commentData.date | formatDate }}</span>
                  </el-col>
                </el-row>
                <span>{{ commentData.comment }}</span>
                <div class="comment-button">
                  <el-button size="mini" icon="el-icon-delete" circle @click="commentRemove(commentData.commentId)"></el-button>
                </div>
              </div>
            </el-col>
          </el-col>
        </el-row>
      </el-row>

      <el-row>
        <el-row justify="center" type="flex">
          <el-col :span="18">
            <el-divider></el-divider> <!--경계선-->
          </el-col>
        </el-row>

        <el-row justify="center" type="flex">
          <el-col :span="18">
            <el-row class="date">
              댓글 작성
            </el-row>
          </el-col>
        </el-row>

        <el-row class="comment-section" justify="center" type="flex">
          <el-col :span="18">
            <el-col :span="5">
              <el-form :model="commentForm" ref="commentForm">
                <el-form-item prop="comment">
                  <el-input type="textarea" v-model="commentForm.comment"></el-input>
                </el-form-item>
                <el-form-item class="icon-area">
                  <el-button type="primary" icon="el-icon-check" size="small" plain @click="submitCommentForm(commentForm)">등록</el-button>
                </el-form-item>
              </el-form>
            </el-col>
          </el-col>
        </el-row>
      </el-row>
    </el-row>
  </main>
</template>

<script>
  import jwt_decode from "jwt-decode";

  export default {
    data() {
      return {
        // 게시글
        boardData: {},
        // 댓글 리스트
        commentData: [],
        // 백엔드에 보내줄 댓글 데이터
        commentForm: {},

        filePath: "C:\\upload\\파일명.txt",
      }
    },
    computed: {
      downloadLink: function () {
        return 'file://' + this.filePath;
      }
    },
    mounted() {
      const boardId = this.$route.params.boardId; // 주소의 boardId 값

      // 특정 게시글 불러오기
      this.$axios.get("/api/board/" + boardId)
        .then(response => {
          this.boardData = response.data;
        })
        .catch(error => {
          alert("게시글을 가져오는 도중 오류가 발생했습니다.")
          console.error("게시글을 가져오는 도중 오류가 발생했습니다.", error);
        })
      
      // 특정 게시글의 댓글 리스트 불러오기
      this.$axios.get("/api/comment/" + boardId)
        .then(response => {
          this.commentData = response.data.sort((a, b) => {
            return b.commentId - a.commentId;
          });
        })
        .catch(error => {
          alert("댓글 리스트를 가져오는 도중 오류가 발생했습니다.")
          console.error("댓글 리스트를 가져오는 중 오류가 발생했습니다.", error);
        })
    },
    methods: {
      boardModify(boardMemberId) {
				const jwtToken = localStorage.getItem("jwtToken"); // 사용자 토큰의 memberId 값
				const decodedToken = jwt_decode(jwtToken);
				const memberId = decodedToken.memberId;

        if (boardMemberId == memberId) {
          const boardId = this.$route.params.boardId; // 주소의 boardId 값
          this.$router.push("/boardform/" + boardId);
        } else {
          alert("사용자가 일치하지 않습니다.");
					console.log("사용자가 일치하지 않습니다.");
        }
      },
      boardRemove() {
        const boardId = this.$route.params.boardId; // 주소의 boardId 값
        const boardMemberId = this.boardData.memberId; // 게시글의 memberId 값

        const jwtToken = localStorage.getItem("jwtToken"); // 사용자 토큰의 memberId 값
				const decodedToken = jwt_decode(jwtToken);
				const memberId = decodedToken.memberId;

        if (boardMemberId == memberId) { // 게시글 작성자와 사용자가 일치하면 진행
          this.$axios.delete("/api/board/" + boardId)
          .then(response => {
            alert("글이 정상적으로 삭제되었습니다!")
            this.$router.push("/boardlist");
          })
          .catch(error => {
            alert("게시글을 삭제하는 도중 오류가 발생했습니다.")
            console.error("게시글을 삭제하는 도중 오류가 발생했습니다.", error);
          });
        } else {
					alert("사용자가 일치하지 않습니다.");
					console.log("사용자가 일치하지 않습니다.");
				}
      },
      submitCommentForm(commentForm) {
        const jwtToken = localStorage.getItem("jwtToken");
        if (jwtToken) { // 사용자가 존재하면 진행
          const decodedToken = jwt_decode(jwtToken);
          this.commentForm.memberId = decodedToken.memberId;

          const boardId = this.$route.params.boardId;
          this.commentForm.boardId = boardId;

          // 댓글 등록
          this.$axios.post("/api/comment", this.commentForm)
          .then(response => {
            alert("댓글이 등록되었습니다!")
            window.location.href = "/boarddetail/" + boardId;
          })
          .catch(error => {
            alert("댓글 등록에 실패했습니다. 다시 시도해주세요.")
            console.error("댓글 등록에 실패했습니다.", error);
          })
        } else {
          alert("로그인후 이용해주세요.");
					console.log("로그인후 이용해주세요.");
        }
      },
      commentRemove(commentId) {
        const commentToDelete = this.commentData.find(comment => comment.commentId === commentId);
        const commentMemberId = commentToDelete.memberId;

        const jwtToken = localStorage.getItem("jwtToken"); // 사용자 토큰의 memberId 값
				const decodedToken = jwt_decode(jwtToken);
				const memberId = decodedToken.memberId;

        console.log("memberId", memberId);
        console.log("commentMemberId", commentToDelete)

        if (commentMemberId == memberId) {// 댓글의 작성자와 사용자가 일치하면 진행
          // 댓글 삭제
          this.$axios.delete("/api/comment/" + commentId)
          .then(response => {
            // 댓글 삭제 성공 시, 화면에서도 삭제
            this.commentData = this.commentData.filter(comment => comment.commentId !== commentId);
            alert("댓글이 삭제되었습니다!");
          })
          .catch(error => {
            alert("댓글 삭제에 실패했습니다. 다시 시도해주세요.");
            console.error("댓글 삭제에 실패했습니다.", error);
          });
        } else {
          alert("사용자가 일치하지 않습니다.");
					console.log("사용자가 일치하지 않습니다.");
        }
      }
    },
    filters: {
      formatDate(date) {
        // replace 경고가 뜨긴하는데 진행에는 문제가 없음 (추후 해결)
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
  .content-top {
    margin-top: 2%;
    padding: 0 40px 0 40px;
  }
  .date, .weather-feel-icon {
    color: gray;
  }
  .icon-area {
    text-align: right;
  }
  .content {
    height: 500px;
    overflow-y: auto;
    overflow-x: auto;
    width: 100%;
  }
  .comment-section {
    margin-top: 2%;
  }
  .comment {
    background-color: #f5f5f5;
    padding: 10px;
    margin-bottom: 10px;
  }
  .comment-details {
    font-size: 12px;
    color: #888;
    margin-top: 5px;
  }
  .comment-button {
    text-align: right;
  }
</style>