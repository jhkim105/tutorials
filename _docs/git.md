# GIT


## Remote Commit Message 수정하기
- 아래 명령 실행
    ```shell
    git rebase -i <commit_hash>^
    ```
- 수정하려는 라인 찾아서 pick → edit 으로 변경 후 저장 (vi editor)
- 작성자 정보 변경하기
    ```shell
    git commit --amend --author="name <email"
    ```
- 수정한 커밋을 계속 진행
    ```shell
    git rebase --continue
    ```
- Push
    ```shell
    git push -f origin <branch name>
    ```

## Config
### 파일 위치
- global: ~/.gitconfig
- local: .git/config

### 사용자 정보
조회
```shell
# global
git config --global user.name
git config --global user.email
# local
git config --local user.name
git config --local user.email

```

변경
```shell
# global
git config --global user.name "<name>"
git config --global user.email "<email>"
# local
git config --local user.name "<name>"
git config --local user.email "<email>"
```