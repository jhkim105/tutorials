### project 지정해서 빌드하기

-pl, --projects      
Build specified reactor projects instead of all projects  

-am, --also-make    
If project list is specified, also build projects required by the list

```
clean install -e -pl web -am
```

