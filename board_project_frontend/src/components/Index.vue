<template>
  <div>
    <el-header height="65px">
      <el-row>
        <el-col :span="5">게시판test</el-col>
        <div class="nav-right-content">
          <div class="user-info" v-if="isLoggedIn">
            <p>안녕하세요, {{ nickname }}님</p>
            <el-button class="logout" @click="logout" size="medium" type="info">로그아웃</el-button>
            <el-button class="myinfo" @click="myinfo" size="medium" type="info">내정보</el-button>
          </div>
          <div class="user-info" v-else>
            <p>지금 로그인을 하여 다양한 기능들을 만나보세요!</p>
            <el-button class="login" @click="login" size="medium" type="info">로그인</el-button>
            <el-button class="signup" @click="signup" size="medium" type="info">회원가입</el-button>
          </div>
        </div>
      </el-row>
    </el-header>

    <el-col class="tac" :span="4">
      <el-col :span="20">
        <h3 class="menu">menu</h3>
        <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
          <el-menu-item index="1" @click="home">
            <i class="el-icon-s-home"></i>
            <span>홈</span>
          </el-menu-item>
          <el-menu-item index="2" @click="boardlist">
            <i class="el-icon-notebook-2"></i>
            <span>자유게시판</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-col>

    <el-main>
      <router-view></router-view>
    </el-main>
  </div>
</template>

<script>
  import jwt_decode from "jwt-decode";

  export default {
    name: "Index",

    data() {
      return {
        memberId: "",
        nickname: "",
        isLoggedIn: false,
      };
    },

    mounted() {
      const jwtToken = localStorage.getItem("jwtToken");

      if (jwtToken) {
        // JWT 토큰이 localStorage에 존재하면 로그인 상태로 처리
        const decodedToken = jwt_decode(jwtToken);
        this.nickname = decodedToken.nickname;
        this.isLoggedIn = true;
      }
    },

    methods: {
      home() {
        let router = this.$router;
        router.push("/");
      },
      signup() {
        let router = this.$router;
        router.push("/signup");
      },
      login() {
        let router = this.$router;
        router.push("/login");
      },
      logout() {
        localStorage.removeItem("jwtToken");
        this.$router.push("/");
        window.location.reload();
      },
      myinfo() {
        const jwtToken = localStorage.getItem("jwtToken");
        if (jwtToken) {
          const decodedToken = jwt_decode(jwtToken);
          this.memberId = decodedToken.memberId;
        }
        this.$router.push("/myinfo/" + this.memberId);
      },
      boardlist() {
        let router = this.$router;
        router.push("/boardlist");
      },
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
</script>

<style scoped>
  .nav-right-content {
    display: flex; /* 내용 요소들을 수평으로 배열 */
    justify-content: flex-end; /* 내용을 오른쪽으로 정렬 */
  }
  .user-info {
    display: flex; /* 요소를 수평으로 배열 */
    align-items: center;
  }
  .nav-name {
    margin-right: 8px;
  }
  .menu {
    text-align: center;
  }
  .el-header {
    padding: 6px 20px 6px 20px;
    background-color: #cacaca;
    color: white;
  }
  .signup, .login, .logout {
    background-color: gray;
    color: white;
  }
</style>