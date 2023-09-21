<template>
  <main>
    <el-row>
      <el-row class="content" type="flex" justify="center">
        <el-col :span="16">
          <h3 class="title">{{ isEditMode ? "글 수정" : "글 작성" }}</h3>
        </el-col>
      </el-row>

      <el-row type="flex" justify="center">
        <el-col :span="16">
          <el-divider></el-divider> <!--경계선-->
        </el-col>
      </el-row>

      <el-row type="flex" justify="center">
        <el-col :span="12">
          <el-form :model="boardForm" ref="boardForm" label-width="40px">
            <el-form-item label="제목" prop="title">
              <el-input v-model="boardForm.title"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>

      <el-row type="flex" justify="center">
        <el-col :span="16">
          <el-divider></el-divider> <!--경계선-->
        </el-col>
      </el-row>

      <el-row type="flex" justify="center">
        <el-col :span="12">
          <el-form :model="boardForm" ref="boardForm" label-width="40px">
            <el-form-item label="내용" prop="content">
              <el-input type="textarea" :rows="15" v-model="boardForm.content"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>

      <el-row type="flex" justify="center">
        <el-col :span="16">
          <el-divider></el-divider> <!--경계선-->
        </el-col>
      </el-row>

      <!-- handlePreview: 파일 미리보기 처리 로직
      handleRemove: 파일 삭제 처리 로직
      beforeRemove: 파일 삭제 전 처리 로직
      handleExceed: 업로드 제한 초과 처리 로직 -->

      <el-row type="flex" justify="center">
        <el-col :span="12">
          <el-upload class="upload-demo" action="/api/upload"
            :on-success="handleSuccess" multiple :limit="1"
            :on-remove="handleRemove">
            <el-button size="small" type="primary" plain><i class="el-icon-upload"></i>업로드</el-button>
            <div slot="tip" class="el-upload__tip">500kb 이하의 jpg/png 파일만 업로드 가능합니다. (최대 파일 1개)</div>
          </el-upload>
        </el-col>
      </el-row>

      <el-row type="flex" justify="center">
        <el-col :span="16">
          <el-divider></el-divider> <!--경계선-->
        </el-col>
      </el-row>

      <el-row type="flex" justify="center">
        <el-col :span="12">
          <el-form :model="boardForm" ref="boardForm" label-width="40px">
            <el-form-item class="icon-area">
              <el-button type="primary" icon="el-icon-check" size="small" plain @click="submitForm(boardForm)">{{ isEditMode ? "수정" : "등록" }}</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>

    </el-row>
  </main>
</template>

<script>
import jwt_decode from "jwt-decode";

export default {
  data() {
    return {
      boardForm: {},
      isEditMode: false,
      file: {}
    }
  },
  mounted() {
    if (this.$route.params.boardId) {
      const boardId = this.$route.params.boardId; // 주소의 boardId 값
      this.isEditMode = true;

      // 특정 게시글 불러오기
      this.$axios.get("/api/board/" + boardId)
        .then(response => {
          this.boardForm = response.data;
        })
        .catch(error => {
          alert("게시글을 가져오는 도중 오류가 발생했습니다.");
          console.error("게시글을 가져오는 도중 오류가 발생했습니다.", error);
        })
    }
  },
  methods: {
    handleSuccess(file) {
      this.file = file;
    },
    handleRemove(uploadFiles) {
      console.log("uploadFiles 출력:", uploadFiles);
      const fileName = uploadFiles.response[0].fileName;
      this.$axios.delete(`/api/upload/${fileName}`)
        .then(response => {
          alert("파일이 삭제되었습니다!")
          console.log("업로드 파일 삭제:", fileName);
        })
        .catch(error => {
          alert("파일 삭제에 실패했습니다.")
          console.error("파일 삭제에 실패했습니다.", error);
        })
    },
    submitForm() {
      if (this.isEditMode) { // 수정 모드
        const boardId = this.$route.params.boardId; // 주소의 boardId 값
        const boardMemberId = this.boardForm.memberId; // 게시글의 memberId 값

				const jwtToken = localStorage.getItem("jwtToken"); // 사용자 토큰의 memberId 값
				const decodedToken = jwt_decode(jwtToken);
				const memberId = decodedToken.memberId;

				if (boardMemberId == memberId) { // 게시글 작성자와 사용자가 일치하면 진행
          // 게시글 수정
          this.$axios.put("/api/board/" + boardId, this.boardForm)
            .then(response => {
              alert("글이 수정되었습니다!");
              this.$router.push("/boardlist")
            })
            .catch(error => {
              alert("글 수정에 실패했습니다.");
              console.error("글 수정에 실패했습니다.", error);
            })
        } else {
					alert("사용자가 일치하지 않습니다.");
					console.log("사용자가 일치하지 않습니다.");
				}
      } else { // 등록 모드
        const jwtToken = localStorage.getItem("jwtToken");
        if (jwtToken) { // 사용자가 존재하면 진행
          const decodedToken = jwt_decode(jwtToken);
          const memberId = decodedToken.memberId;
          this.boardForm.memberId = memberId;

          const formData = new FormData();
          formData.append("title", this.boardForm.title)
          formData.append("content", this.boardForm.content)
          formData.append("memberId", this.boardForm.memberId)
          formData.append("fileName", this.file[0].fileName)

          // 게시글 등록
          this.$axios.post("/api/board", formData)
            .then(response => {
              alert("글이 등록되었습니다!")
              this.$router.push("/boardlist")
            })
            .catch(error => {
              alert("글 등록에 실패했습니다.")
              console.error("글 등록에 실패했습니다.", error);
            })
        } else {
					alert("로그인후 이용해주세요.");
					console.log("로그인후 이용해주세요.");
          this.$router.push("/boardlist");
				}
      }
    }
  }
}
</script>

<style scpoed>
  .content {
		margin-top: 2%;
	}
	.title {
    text-align: center;
  }
  .icon-area {
    text-align: right;
  }
</style>